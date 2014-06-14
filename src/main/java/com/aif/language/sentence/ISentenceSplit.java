package com.aif.language.sentence;

import java.util.List;

public interface ISentenceSplit {

    public List<List<String>> parseSentences(List<String> tokens);

}
