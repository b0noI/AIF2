package io.aif.language.semantic;

import io.aif.language.common.FuzzyBoolean;
import io.aif.language.common.IFuzzyBoolean;
import io.aif.language.common.IMapper;
import io.aif.language.semantic.weights.IProperNounCalculator;
import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.word.IWord;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by b0noI on 06/02/2015.
 */
class SemanticGraphBuilder implements IMapper<Map<IWord, List<IWord>>, List<ISemanticNode<IWord>>> {
    
    private final INodeWeightCalculator<IWord> nodeWeightCalculator;
    
    private final IEdgeWeightCalculator<IWord> edgeWeightCalculator;

    private final IProperNounCalculator properNounCalculator;

    SemanticGraphBuilder(final INodeWeightCalculator<IWord> nodeWeightCalculator, 
                         final IEdgeWeightCalculator<IWord> edgeWeightCalculator,
                         final IProperNounCalculator properNounCalculator) {
        this.nodeWeightCalculator = nodeWeightCalculator;
        this.edgeWeightCalculator = edgeWeightCalculator;
        this.properNounCalculator = properNounCalculator;
    }

    @Override
    public List<ISemanticNode<IWord>> map(final Map<IWord, List<IWord>> words) {
        final List<ISemanticNode<IWord>> nodes = words
                .keySet()
                .stream()
                .map(key -> {
                    //TODO Need to find out a way to send partial builders for IFuzzyBoolean
                    return new SemanticWord(key,
                                            nodeWeightCalculator.calculateWeight(key),
                                            new FuzzyBoolean(properNounCalculator.calculate(key)));
                })
                .collect(Collectors.toList());    
        
        nodes.forEach(node -> {
            final List<IWord> connections = words.get(node.item());
            final Map<ISemanticNode<IWord>, Double> semanticConnections = new HashMap<>();
            connections.forEach(connect -> {
                final Optional<ISemanticNode<IWord>> optTarget = nodes
                        .stream()
                        .filter(n -> n.item() == connect)
                        .findFirst();
                if (optTarget.isPresent())
                    semanticConnections.put(optTarget.get(), edgeWeightCalculator.calculateWeight(node.item(), optTarget.get().item()));
            });
            ((SemanticWord)node).setConnections(semanticConnections);
        });
        return nodes;
    }
    
    private static class SemanticWord implements ISemanticNode<IWord> {

        private final IWord word;
        
        private final Double weight;
        
        private Map<ISemanticNode<IWord>, Double> connections = null;

        private IFuzzyBoolean isProperNoun;

        private SemanticWord(final IWord word, final Double weight, final IFuzzyBoolean isProperNoun) {
            this.word = word;
            this.weight = weight;
            this.isProperNoun = isProperNoun;
        }

        @Override
        public double weight() {
            return weight;
        }

        @Override
        public double connectionWeight(final ISemanticNode<IWord> semanticNode) {
            return connections.get(semanticNode);
        }

        @Override
        public Set<ISemanticNode<IWord>> connectedItems() {
            return connections.keySet();
        }

        @Override
        public IWord item() {
            return word;
        }
        
        void setConnections(final Map<ISemanticNode<IWord>, Double> connections) {
            this.connections = connections;
        }

        @Override
        public IFuzzyBoolean isProperNoun() { return isProperNoun; }
    }
}
