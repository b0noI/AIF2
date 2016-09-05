package io.aif.language.semantic.weights.edge.word;

import java.util.Map;

import io.aif.language.word.IWord;

class WordEdgeWeightCalculator implements IWordEdgeWeightCalculator {

  private final Map<IWord, Map<IWord, Double>> distancesGraph;

  public WordEdgeWeightCalculator(final Map<IWord, Map<IWord, Double>> distancesGraph) {
    this.distancesGraph = distancesGraph;
  }

  @Override
  public double calculateWeight(final IWord from, final IWord to) {
    return distancesGraph.get(from).get(to);
  }

}
