package io.aif.language.common;

import io.aif.language.common.IDict;

public interface IDictBuilder<T, R> {

    public IDict<R> build(final T tokens);

}
