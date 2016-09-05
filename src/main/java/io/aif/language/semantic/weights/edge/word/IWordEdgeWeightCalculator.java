package io.aif.language.semantic.weights.edge.word;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.word.IWord;

public interface IWordEdgeWeightCalculator extends IEdgeWeightCalculator<IWord> {

  public static IWordEdgeWeightCalculator generateDefaultWeightCalculator(
      final Map<IWord, Map<IWord, List<Double>>> distancesGraph) {

    final Set<IWordEdgeWeightCalculator> calculators = new HashSet<>();
    calculators.add(new WordEdgeWeightCalculator(
        RawGraphConverter.recalculateConnections(distancesGraph)));

    return new CompositeEdgeWeightCalculator(calculators);
  }

}
