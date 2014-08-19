package com.aif.language.semantic.weights.node;


import com.aif.language.semantic.ISemanticNode;

public interface INodeWeightCalculator<T> {

    public double calculateWeight(final ISemanticNode<T> semanticNode);

}
