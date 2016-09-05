package io.aif.language.semantic.weights.edge;

public interface IEdgeWeightCalculator<T> {

  // TODO: rename to calculate
  public double calculateWeight(final T from, final T to);

}
