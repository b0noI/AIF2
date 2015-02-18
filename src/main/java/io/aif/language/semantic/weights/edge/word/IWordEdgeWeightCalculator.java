package io.aif.language.semantic.weights.edge.word;

import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.word.IWord;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by vsk on 2/17/15.
 */
public interface IWordEdgeWeightCalculator extends IEdgeWeightCalculator<IWord> {

    public static IWordEdgeWeightCalculator generateDefaultWeightCalculator(final Map<IWord, Map<IWord, List<Double>>> distancesGraph) {
        final Set<IWordEdgeWeightCalculator> calculators = new HashSet<>();

        calculators.add(new WordEdgeWeightCalculator(RawGraphConverter.recalculateConnections(distancesGraph)));

        return new CompositeEdgeWeightCalculator(calculators);
    }

}
