package com.aif.language.semantic.weights;


import com.aif.language.semantic.ISemanticNode;

public interface IWeightCalculator<T> {

    public double calculateWeight(final ISemanticNode<T> semanticNode);

}
