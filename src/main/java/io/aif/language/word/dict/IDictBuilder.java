package io.aif.language.word.dict;

import io.aif.language.word.IWord;

import java.util.List;

public interface IDictBuilder<T> {

    public List<IWord> build(T tokens);

}
