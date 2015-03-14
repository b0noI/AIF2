package io.aif.language.semantic;

import io.aif.language.common.IFuzzyBoolean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;

public interface ISemanticNode<T> {

    @Max(value = 1)
    @Min(value = 0)
    public double                   weight();

    @Max(value = 1)
    @Min(value = 0)
    public double                   connectionWeight(ISemanticNode<T> semanticNode);

    public Set<ISemanticNode<T>>    connectedItems();

    public T                        item();

    public IFuzzyBoolean            isProperNoun();
}
