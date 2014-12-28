package io.aif.language.word.dict;

import io.aif.language.word.IDict;

public interface IDictBuilder<T> {

    public IDict build(final T tokens);

}
