package io.aif.language.word.mapper;


import io.aif.language.word.IDict;
import io.aif.language.word.IWord;

import java.util.List;

public interface IWordMapper<T> {

    public List<T> map(final List<IWord> from, final IDict dict);

}
