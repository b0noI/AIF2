package com.aif.language.semantic;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

public interface ISemanticNode<T> {

    @Max(value = 1)
    @Min(value = 0)
    public double weight();

    @Max(value = 1)
    @Min(value = 0)
    public double connectionWeight(T semanticNode);

    public List<T> connectedItems();
    
}
