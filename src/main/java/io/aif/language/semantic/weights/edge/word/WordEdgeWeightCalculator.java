package io.aif.language.semantic.weights.edge.word;

import com.google.common.annotations.VisibleForTesting;
import io.aif.language.semantic.ISemanticNode;
import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.word.IWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;


public class WordEdgeWeightCalculator implements IEdgeWeightCalculator<IWord> {
    
    // public zone
    
    public static WordEdgeWeightCalculator generateWordEdgeWeightCalculator(final Map<IWord, Map<IWord, List<Double>>> distancesGraph) {
        final Map<IWord, Double> maxConnections =  WordEdgeWeightCalculator.maxConnections(distancesGraph);
        return new WordEdgeWeightCalculator(distancesGraph, maxConnections);
    }

    @Override
    public double calculateWeight(final IWord from, final IWord to) {
        return WordEdgeWeightCalculator.getAverageDistance(distancesGraph, from, to) / maxConnection.get(from);
    }
    
    // protected zone

    @VisibleForTesting
    protected static double getAverageDistance(final Map<IWord, Map<IWord, List<Double>>> distancesGraph, 
                                               final IWord from, 
                                               final IWord to) {
        return distancesGraph.get(from)
                .get(to)
                .stream()
                .collect(Collectors.summarizingDouble(x -> x))
                .getAverage();
    }

    @VisibleForTesting
    protected static final Map<IWord, Double> maxConnections(final Map<IWord, Map<IWord, List<Double>>> distancesGraph) {
        final Map<IWord, Double> results = new HashMap<>();
        distancesGraph.keySet().stream().forEach(key -> {
            final OptionalDouble optMax = distancesGraph
                    .get(key)
                    .keySet()
                    .stream()
                    .map(innerKey -> distancesGraph.get(key).get(innerKey))
                    .mapToDouble(distances -> distances.stream().collect(Collectors.summarizingDouble(x -> x)).getAverage())
                    .max();
            results.put(key, optMax.isPresent() ? optMax.getAsDouble(): .0);
        });
        return results;
    }
    
    // private zone

    private final Map<IWord, Map<IWord, List<Double>>> distancesGraph;

    private final Map<IWord, Double> maxConnection;
    
    private WordEdgeWeightCalculator(final Map<IWord, Map<IWord, List<Double>>> distancesGraph,
                                     final Map<IWord, Double> maxConnection) {
        this.distancesGraph = distancesGraph;
        this.maxConnection = maxConnection;
    }
    
}
