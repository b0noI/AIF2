package com.aif.associations.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public interface IItem extends Comparable<IItem> {

    @Min(value = 0)
    @Max(value = 1)
    public double getWeight();

    @Min(value = 0)
    @Max(value = 1)
    public double getComplexety();

    default public int compareTo(final IItem item) {
        if (item.getWeight() == getWeight()) return 0;
        if (item.getWeight() > getWeight()) return -1;
        return 1;
    }

}
