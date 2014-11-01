package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;

import java.util.Set;

public class CompositeNodeWeightCalculator<T> implements INodeWeightCalculator<T> {

    private final Set<INodeWeightCalculator<T>> calculators;

    public CompositeNodeWeightCalculator(Set<INodeWeightCalculator<T>> calculators) {
        this.calculators = calculators;
    }

    @Override
    public double calculateWeight(ISemanticNode<T> semanticNode) {
        final double weightSum = calculators.stream()
                .mapToDouble(calculator -> calculator.calculateWeight(semanticNode))
                .sum();
        return weightSum / calculators.size();
    }

}
