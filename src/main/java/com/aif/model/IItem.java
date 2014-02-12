package com.aif.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public interface IItem {

    @Min(value = 0)
    @Max(value = 1)
    public double getWeight();

    @Min(value = 0)
    @Max(value = 1)
    public double getComplexety();

}
