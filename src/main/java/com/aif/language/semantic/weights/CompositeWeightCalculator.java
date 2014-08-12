package com.aif.language.semantic.weights;

import com.aif.language.semantic.ISemanticNode;

import java.util.HashSet;
import java.util.Set;

public class CompositeWeightCalculator<T> implements IWeightCalculator<T> {

    private final Set<IWeightCalculator<T>> calculators = new HashSet<>();

    @Override
    public double calculateWeight(ISemanticNode<T> semanticNode) {
        final double weightSum = calculators.stream()
                .mapToDouble(calculator -> calculator.calculateWeight(semanticNode))
                .sum();
        return weightSum / calculators.size();
    }

}
