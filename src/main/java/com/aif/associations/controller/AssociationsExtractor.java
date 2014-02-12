package com.aif.associations.controller;

import com.aif.associations.model.IAssociationsExtractor;
import com.aif.associations.model.IConnectionExtractor;
import com.aif.associations.model.IItem;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class AssociationsExtractor implements IAssociationsExtractor {

    @Min(value = 0)
    @Max(value = 1)
    @Override
    public double getAssociationsLevel(IConnectionExtractor connectionExtractor, IItem item1, IItem item2) {
        return 0;
    }

}
