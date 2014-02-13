package com.aif.associations.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public interface IConnectionExtractor<T extends IItem, T2 extends IItem> {

    @Min(value = 0)
    @Max(value = 1)
    public double getProbabilityOfHavingItemsInOneExperiment(final T item1, final T2 item2);

    @Min(value = 0)
    @Max(value = 1)
    public double getApproximateIntervalBetweenItems(final T item1, final T2 item2);

}
