package com.aif.associations.controller;

import com.aif.associations.model.IAssociationsExtractor;
import com.aif.associations.model.IConnectionExtractor;
import com.aif.associations.model.IItem;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public final class AssociationsExtractor implements IAssociationsExtractor {

    private static final double NUMERATOR_MULTIPLIER = 2.0;

    @Min(value = 0)
    @Max(value = 1)
    @Override
    public double getAssociationsLevel(final IConnectionExtractor connectionExtractor, final IItem item1, final IItem item2) {
        final double numerator = calculateNumerator(connectionExtractor, item1, item2);
        final double denominator = calculateDenominator(connectionExtractor, item1, item2);
        return numerator / denominator;
    }

    private static double calculateNumerator(final IConnectionExtractor connectionExtractor, final IItem item1, final IItem item2) {
        final double probabilityOfHavingItemsInOneExperiment = connectionExtractor.getProbabilityOfHavingItemsInOneExperiment(item1, item2);
        final double aproxWeight = (item1.getWeight() + item2.getWeight()) / 2.0;
        return NUMERATOR_MULTIPLIER * aproxWeight * Math.exp(probabilityOfHavingItemsInOneExperiment);
    }

    private static double calculateDenominator(IConnectionExtractor connectionExtractor, IItem item1, IItem item2) {
        final double itemsDifficult = (item1.getComplexety() + item2.getComplexety()) / 2.0;
        final double aproxInterval = connectionExtractor.getApproximateIntervalBetweenItems(item1, item2);
        return itemsDifficult * aproxInterval;
    }

}
