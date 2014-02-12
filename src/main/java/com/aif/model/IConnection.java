package com.aif.model;

public interface IConnection {

    public double getProbabilityOfHavingItemsInOneExperiment(IItem item1, IItem item2);

    public double getApproximateIntervalBetweenItems(IItem item1, IItem item2);

}
