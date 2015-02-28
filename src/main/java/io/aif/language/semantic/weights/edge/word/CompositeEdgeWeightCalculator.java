package io.aif.language.semantic.weights.edge.word;

import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.word.IWord;

import java.util.Set;


class CompositeEdgeWeightCalculator implements IWordEdgeWeightCalculator {

    // public zone

    public CompositeEdgeWeightCalculator(final Set<IWordEdgeWeightCalculator> calculators) {
        this.calculators = calculators;
    }

    @Override
    public double calculateWeight(final IWord from, final IWord to) {
        final double weightSum = calculators.stream()
                .mapToDouble(calculator -> calculator.calculateWeight(from, to))
                .sum();
        return weightSum / (double)calculators.size();
    }

    // private zone

    private final Set<IWordEdgeWeightCalculator> calculators;

}
