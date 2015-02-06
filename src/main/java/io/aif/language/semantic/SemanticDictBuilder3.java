package io.aif.language.semantic;

import io.aif.language.common.IDictBuilder;
import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.semantic.weights.edge.word.WordEdgeWeightCalculator;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.semantic.weights.node.word.CompositeNodeWeightCalculator;
import io.aif.language.semantic.weights.node.word.IWordWeightCalculator;
import io.aif.language.word.IWord;

import java.util.*;
import java.util.stream.Collectors;

public class SemanticDictBuilder3 implements IDictBuilder<Collection<IWord.IWordPlaceholder>, ISemanticNode<IWord>> {

    private final int connectAhead;
    private final Map<IWord, Map<IWord, List<Double>>> iwordToSemanticWordCache = new HashMap<>();

    public static final int CONNECT_AHEAD_TILL_END = -1;

    public static final int DONT_CONNECT = 0;

    public SemanticDictBuilder3() {
        this.connectAhead = CONNECT_AHEAD_TILL_END;
    }

    public SemanticDictBuilder3(int connectAhead) {
        if (connectAhead < CONNECT_AHEAD_TILL_END)
            throw new IllegalArgumentException(
                    "Connect ahead parameter should be greater than -1, given"  + connectAhead);
        this.connectAhead = connectAhead;
    }

    @Override
    public ISemanticDict build(Collection<IWord.IWordPlaceholder> placeholders) {
        final List<IWord> semanticWords = placeholders
                .stream()
                .map(IWord.IWordPlaceholder::getWord)
                .collect(Collectors.toList());

        final int subListLen = (connectAhead < 0 || connectAhead > semanticWords.size())
                ? semanticWords.size()
                : connectAhead;

        IWord subjectNode;
        List<IWord> subList;
        for (int i = 0; i < semanticWords.size(); i++) {
            subjectNode = semanticWords.get(i);
            subList = semanticWords.subList(1, subListLen);
            for (int j = 1; j < subList.size(); j++) {
                if (!iwordToSemanticWordCache.containsKey(subjectNode)) {
                    iwordToSemanticWordCache.put(subjectNode, new HashMap<>());
                }
                final IWord tNode = semanticWords.get(i + j);
                if (!iwordToSemanticWordCache.get(subjectNode).containsKey(tNode)) {
                    iwordToSemanticWordCache.get(subjectNode).put(tNode, new ArrayList<>());
                }
                iwordToSemanticWordCache.get(subjectNode).get(tNode).add((double)(j - i));
            }
        }


        final IEdgeWeightCalculator<IWord> edgeWeightCalculator = WordEdgeWeightCalculator.generateWordEdgeWeightCalculator(iwordToSemanticWordCache);
        final INodeWeightCalculator<IWord> nodeWeightCalculator = IWordWeightCalculator.createDefaultWeightCalculator(edgeWeightCalculator, iwordToSemanticWordCache);
        
        final SemanticGraphBuilder semanticGraphBuilder = new SemanticGraphBuilder(nodeWeightCalculator, edgeWeightCalculator);
        
        final Map<IWord, List<IWord>> connectionsMap = new HashMap<>();
        iwordToSemanticWordCache.keySet().forEach(connect -> {
            connectionsMap.put(connect, new ArrayList<>(iwordToSemanticWordCache.get(connect).keySet()));
        });

        return new SemanticDict(new HashSet<>(semanticGraphBuilder.map(connectionsMap)));
    }

}
