package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;

import java.util.Set;

public class CompositeNodeWeightCalculator<T> implements INodeWeightCalculator<T> {

    // public zone
    
    public CompositeNodeWeightCalculator(final Set<INodeWeightCalculator<T>> calculators) {
        this.calculators = calculators;
    }

    @Override
    public double calculateWeight(final T semanticNode) {
        final double weightSum = calculators.stream()
                .mapToDouble(calculator -> calculator.calculateWeight(semanticNode))
                .sum();
        return weightSum / (double)calculators.size();
    }
    
    // private zone

    private final Set<INodeWeightCalculator<T>> calculators;
    
}
