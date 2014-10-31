package com.aif.language.word.dict;

import com.aif.language.common.IExtractor;
import com.aif.language.word.IWord;

import java.util.Collection;
import java.util.List;

public interface IDictBuilder<T> {

    public List<IWord> build(T tokens);

}
