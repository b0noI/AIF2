package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;

public class ConnectionBasedWeightCalculator implements IWordWeightCalculator {
    
    // public zone

    public ConnectionBasedWeightCalculator(final IEdgeWeightCalculator<IWord> edgeWeightCalculator, 
                                           final Map<IWord, Map<IWord, List<Double>>> distancesGraph) {
        this.edgeWeightCalculator = edgeWeightCalculator;
        this.distancesGraph = distancesGraph;
    }

    @Override
    public double calculateWeight(final IWord node) {
        final Set<IWord> items = distancesGraph.get(node).keySet();

        final OptionalDouble maxConnectionWeightOptional = items
                .parallelStream()
                .mapToDouble(word -> edgeWeightCalculator.calculateWeight(node, word))
                .max();

        if (!maxConnectionWeightOptional.isPresent())
            return 0;

        final double maxConnectionWeight = maxConnectionWeightOptional.getAsDouble();
        final double normalizedConnectionCount = (double)items.size() / (double)MAX_WORD_CONNECTIONS_COUNT;

        return maxConnectionWeight * (1. - normalizedConnectionCount);
    }
    
    // private zone

    private final IEdgeWeightCalculator<IWord> edgeWeightCalculator;

    private final Map<IWord, Map<IWord, List<Double>>> distancesGraph;

    private static  final int MAX_WORD_CONNECTIONS_COUNT = 20_000;

}
