package com.aif.associations.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public interface IConnectionExtractor {

    @Min(value = 0)
    @Max(value = 1)
    public double getProbabilityOfHavingItemsInOneExperiment(IItem item1, IItem item2);

    @Min(value = 0)
    @Max(value = 1)
    public double getApproximateIntervalBetweenItems(IItem item1, IItem item2);

}
