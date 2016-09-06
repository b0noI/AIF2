package io.aif.language.semantic.weights.edge.word;

import java.util.Set;

import io.aif.language.word.IWord;

class CompositeEdgeWeightCalculator implements IWordEdgeWeightCalculator {

  private final Set<IWordEdgeWeightCalculator> calculators;

  public CompositeEdgeWeightCalculator(final Set<IWordEdgeWeightCalculator> calculators) {
    this.calculators = calculators;
  }

  @Override
  public double calculateWeight(final IWord from, final IWord to) {
    final double weightSum = calculators.stream()
        .mapToDouble(calculator -> calculator.calculateWeight(from, to))
        .sum();
    return weightSum / (double) calculators.size();
  }

}
