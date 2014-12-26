package io.aif.language.word.dict;

import io.aif.language.word.IDict;
import io.aif.language.word.IWord;

import java.util.Collection;
import java.util.List;

public interface IDictBuilder<T> {

    public IDict build(final T tokens);

}
