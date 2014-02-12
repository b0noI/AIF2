package com.aif.associations.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public interface IAssociationsExtractor {

    @Min(value = 0)
    @Max(value = 1)
    public double getAssociationsLevel(final IConnectionExtractor connectionExtractor, final IItem item1, final IItem item2);

}
