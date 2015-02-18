package io.aif.language.semantic;

import io.aif.language.common.IDictBuilder;
import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.semantic.weights.edge.word.IWordEdgeWeightCalculator;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.semantic.weights.node.word.CompositeNodeWeightCalculator;
import io.aif.language.semantic.weights.node.word.IWordWeightCalculator;
import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassifier;
import io.aif.language.word.IWord;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class SemanticDictBuilder3 implements IDictBuilder<Collection<IWord.IWordPlaceholder>, ISemanticNode<IWord>> {

    private final int connectAhead;
    
    private static final Logger LOGGER = Logger.getLogger(SemanticDictBuilder3.class);
    
    private final Map<IWord, Map<IWord, List<Double>>> iwordToSemanticWordCache = new HashMap<>();
    
    private final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separators;

    public SemanticDictBuilder3(final int connectAhead,
                                final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separators) {
        this.connectAhead = connectAhead;
        this.separators = separators;
    }

    @Override
    public ISemanticDict build(final Collection<IWord.IWordPlaceholder> placeholders) {
        final List<IWord> semanticWords = placeholders
                .stream()
                .map(IWord.IWordPlaceholder::getWord)
                .collect(Collectors.toList());

        final int subListLen = (connectAhead < 0 || connectAhead > semanticWords.size())
                ? semanticWords.size()
                : connectAhead;

        IWord subjectNode;
        for (int i = 0; i < semanticWords.size() - subListLen - 1; i++) {
            int multiplayer = 1;
            LOGGER.debug(String.format("Done: %f percent", (double)i / (double)semanticWords.size()));
            subjectNode = semanticWords.get(i);
            for (int j = 1; j < subListLen; j++) {
                if (!iwordToSemanticWordCache.containsKey(subjectNode)) {
                    iwordToSemanticWordCache.put(subjectNode, new HashMap<>());
                }
                final IWord tNode = semanticWords.get(i + j);
                
                if (separators.get(ISeparatorGroupsClassifier.Group.GROUP_2).contains(tNode.getRootToken())) {
                    multiplayer += 1;
                }
                if (separators.get(ISeparatorGroupsClassifier.Group.GROUP_1).contains(tNode.getRootToken())) {
                    multiplayer += 4;
                }
                
                if (!iwordToSemanticWordCache.get(subjectNode).containsKey(tNode)) {
                    iwordToSemanticWordCache.get(subjectNode).put(tNode, new ArrayList<>());
                }
                iwordToSemanticWordCache.get(subjectNode).get(tNode).add((double)(j * multiplayer));
            }
        }


        final IEdgeWeightCalculator<IWord> edgeWeightCalculator = IWordEdgeWeightCalculator.generateDefaultWeightCalculator(iwordToSemanticWordCache);
        final INodeWeightCalculator<IWord> nodeWeightCalculator = IWordWeightCalculator.createDefaultWeightCalculator(edgeWeightCalculator, iwordToSemanticWordCache);
        
        final SemanticGraphBuilder semanticGraphBuilder = new SemanticGraphBuilder(nodeWeightCalculator, edgeWeightCalculator);
        
        final Map<IWord, List<IWord>> connectionsMap = new HashMap<>();
        iwordToSemanticWordCache.keySet().forEach(connect -> {
            connectionsMap.put(connect, new ArrayList<>(iwordToSemanticWordCache.get(connect).keySet()));
        });

        return new SemanticDict(new HashSet<>(semanticGraphBuilder.map(connectionsMap)));
    }

}
