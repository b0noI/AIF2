package com.aif.language.sentence;

import com.aif.language.common.ISplitter;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StanfordNLPSentenceSplitter implements ISplitter<Reader, String> {

    @Override
    public List<String> split(Reader target) {

        List<String> sentenceList = new LinkedList<String>();

        DocumentPreprocessor dp = new DocumentPreprocessor(target);
        Iterator<List<HasWord>> iterator = dp.iterator();

        while (iterator.hasNext()) {

            StringBuilder sentenceSb = new StringBuilder();
            List<HasWord> sentence = iterator.next();
            for (HasWord token : sentence) {
                if(sentenceSb.length() > 1) {
                    sentenceSb.append(" ");
                }
                sentenceSb.append(token);
            }
            sentenceList.add(sentenceSb.toString());
        }

        return sentenceList;

    }



}
