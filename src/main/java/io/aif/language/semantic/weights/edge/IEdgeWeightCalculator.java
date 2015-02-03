package io.aif.language.semantic.weights.edge;

import io.aif.language.semantic.ISemanticNode;


public interface IEdgeWeightCalculator<T> {

    public double calculateWeight(final ISemanticNode<T> from, final ISemanticNode<T> to);
    
}
