package io.aif.language.semantic;

import io.aif.language.common.IMapper;
import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.word.IWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by b0noI on 06/02/2015.
 */
class SemanticGraphBuilder implements IMapper<Map<IWord, List<IWord>>, List<ISemanticNode<IWord>>> {
    
    private final INodeWeightCalculator<IWord> nodeWeightCalculator;
    
    private final IEdgeWeightCalculator<IWord> edgeWeightCalculator;

    SemanticGraphBuilder(final INodeWeightCalculator<IWord> nodeWeightCalculator, 
                         final IEdgeWeightCalculator<IWord> edgeWeightCalculator) {
        this.nodeWeightCalculator = nodeWeightCalculator;
        this.edgeWeightCalculator = edgeWeightCalculator;
    }

    @Override
    public List<ISemanticNode<IWord>> map(final Map<IWord, List<IWord>> words) {
        final List<ISemanticNode<IWord>> nodes = words
                .keySet()
                .stream()
                .map(key -> new SemanticWord(key, nodeWeightCalculator.calculateWeight(key)))
                .collect(Collectors.toList());    
        
        nodes.forEach(node -> {
            final List<IWord> connections = words.get(node.item());
            final Map<ISemanticNode<IWord>, Double> semanticConnections = new HashMap<>();
            connections.forEach(connect -> {
                final ISemanticNode<IWord> target = nodes
                        .stream()
                        .filter(n -> n.item() == connect)
                        .findFirst()
                        .get();
                semanticConnections.put(target, edgeWeightCalculator.calculateWeight(node.item(), target.item()));
            });
            ((SemanticWord)node).setConnections(semanticConnections);
        });
        return nodes;
    }
    
    private static class SemanticWord implements ISemanticNode<IWord> {

        private final IWord word;
        
        private final Double weight;
        
        private Map<ISemanticNode<IWord>, Double> connections = null;

        private SemanticWord(final IWord word, final Double weight) {
            this.word = word;
            this.weight = weight;
        }

        @Override
        public double weight() {
            return 0;
        }

        @Override
        public double connectionWeight(ISemanticNode<IWord> semanticNode) {
            return 0;
        }

        @Override
        public Set<ISemanticNode<IWord>> connectedItems() {
            return null;
        }

        @Override
        public IWord item() {
            return word;
        }
        
        void setConnections(final Map<ISemanticNode<IWord>, Double> connections) {
            this.connections = connections;
        }
        
    }
    
}
