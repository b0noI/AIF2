package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;

import java.util.Set;

public class CompositeNodeWeightCalculator<T> implements INodeWeightCalculator<T> {

    // public zone
    
    public CompositeNodeWeightCalculator(final Set<INodeWeightCalculator<T>> calculators, 
                                         final boolean inverted) {
        this.calculators = calculators;
        this.inverted = inverted;
    }

    @Override
    public double calculateWeight(final T semanticNode) {
        final double weightSum = calculators.stream()
                .mapToDouble(calculator -> calculator.calculateWeight(semanticNode))
                .sum();
        final double weight = weightSum / (double)calculators.size();
        return inverted ? 1. - weight : weight;
    }
    
    // private zone

    private final Set<INodeWeightCalculator<T>> calculators;
    
    private final boolean inverted;
    
}
