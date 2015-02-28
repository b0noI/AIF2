package io.aif.language.semantic.weights.edge.word;

import com.google.common.annotations.VisibleForTesting;
import io.aif.language.word.IWord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;


class WordEdgeWeightCalculator implements IWordEdgeWeightCalculator {
    
    // public zone

    public WordEdgeWeightCalculator(final Map<IWord, Map<IWord, Double>> distancesGraph) {
        this.distancesGraph = distancesGraph;
    }

    @Override
    public double calculateWeight(final IWord from, final IWord to) {
        return distancesGraph.get(from).get(to);
    }

    // private zone

    private final Map<IWord, Map<IWord, Double>> distancesGraph;
    
}
