package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

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

//        final OptionalDouble maxConnectionWeightOptional = items
//                .parallelStream()
//                .mapToDouble(word -> edgeWeightCalculator.calculateWeight(node, word))
//                .max();

        final Double averageWeightOptional = items
                .parallelStream()
                .map(word -> edgeWeightCalculator.calculateWeight(node, word))
                .collect(Collectors.summarizingDouble(x -> x))
                .getAverage();
        
//        if (!maxConnectionWeightOptional.isPresent())
//            return 0;
//
//        final double maxConnectionWeight = maxConnectionWeightOptional.getAsDouble();
//        final double normalizedConnectionCount = (double)items.size() / (double)MAX_WORD_CONNECTIONS_COUNT;
//
//        return maxConnectionWeight * (1. - normalizedConnectionCount);
        return 1. - Math.abs(TARGET - averageWeightOptional);
    }
    
    // private zone

    private final IEdgeWeightCalculator<IWord> edgeWeightCalculator;

    private final Map<IWord, Map<IWord, List<Double>>> distancesGraph;

    private static  final double TARGET = .7;

}
