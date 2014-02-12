package com.aif.associations.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public interface IConnectionExtractor {

    @Min(value = 0)
    @Max(value = 1)
    public double getProbabilityOfHavingItemsInOneExperiment(final IItem item1, final IItem item2);

    @Min(value = 0)
    @Max(value = 1)
    public double getApproximateIntervalBetweenItems(final IItem item1, final IItem item2);

}
