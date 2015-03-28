package io.aif.language.semantic;

import io.aif.language.common.IDictBuilder;
import io.aif.language.semantic.weights.IProperNounCalculator;
import io.aif.language.semantic.weights.TitleCaseProperNounCalculator;
import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.semantic.weights.edge.word.IWordEdgeWeightCalculator;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.semantic.weights.node.word.IWordWeightCalculator;
import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassifier;
import io.aif.language.word.IWord;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class SemanticDictBuilder implements IDictBuilder<Collection<IWord.IWordPlaceholder>, ISemanticNode<IWord>> {

    private final int connectAhead;

    private final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separators;

    public SemanticDictBuilder(final int connectAhead,
                               final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separators) {
        this.connectAhead = connectAhead;
        this.separators = separators;
    }

    public SemanticDictBuilder(final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separators) {
        this(0, separators);
    }

    @Override
    public ISemanticDict build(final Collection<IWord.IWordPlaceholder> placeholders) {
        RawGraphBuilder graphBuilder = new RawGraphBuilder(connectAhead, separators);
        Map<IWord, Map<IWord, List<Double>>> rawGraph = graphBuilder.build(placeholders);

        final IEdgeWeightCalculator<IWord> edgeWeightCalculator = IWordEdgeWeightCalculator.generateDefaultWeightCalculator(rawGraph);
        final INodeWeightCalculator<IWord> nodeWeightCalculator = IWordWeightCalculator.createDefaultWeightCalculator(edgeWeightCalculator, rawGraph);
        final IProperNounCalculator properNounCalculator = IProperNounCalculator.getDefault();
        
        final SemanticGraphBuilder semanticGraphBuilder = new SemanticGraphBuilder(nodeWeightCalculator, edgeWeightCalculator, properNounCalculator);
        
        final Map<IWord, List<IWord>> connectionsMap = new HashMap<>();
        rawGraph.keySet().forEach(connect -> {
            connectionsMap.put(connect, new ArrayList<>(rawGraph.get(connect).keySet()));
        });

        return SemanticDict.create(new HashSet<>(semanticGraphBuilder.map(connectionsMap)));
    }

}
