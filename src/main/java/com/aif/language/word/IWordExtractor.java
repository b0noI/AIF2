package com.aif.language.word;

import java.util.List;

public interface IWordExtractor {

    public List<AbstractWord.WordPlaceHolder> getWords(List<List<String>> sentances);

}
