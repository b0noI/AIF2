package io.aif.language.semantic.weights.node;


import io.aif.language.semantic.ISemanticNode;

public interface INodeWeightCalculator<T> {

    public double calculateWeight(final ISemanticNode<T> semanticNode);

}
