package com.aif.language.sentence;

import com.aif.language.common.ISplitter;
import com.aif.language.sentence.splitters.AbstractSentenceSplitter;
import com.aif.language.token.TokenSplitter;

import java.util.List;

class AIF2NLPSentenceSplitter implements ISplitter<String, List<String>>{

    private final TokenSplitter tokenSplitter = new TokenSplitter();
    private final AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();

    public List<List<String>> split(String target) {

        List<String> tokens = tokenSplitter.split(target);
        List<List<String>> sentenceList = sentenceSplitter.split(tokens);

        return sentenceList;

    }



}
