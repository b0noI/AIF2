package com.aif.language.sentence;

import com.aif.language.common.ISplitter;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class StanfordNLPSentenceSplitter implements ISplitter<String, String> {

    @Override
    public List<String> split(String target) {

        List<String> sentenceList = new LinkedList<>();

        try (Reader targetReader = new StringReader(target)) {

            DocumentPreprocessor dp = new DocumentPreprocessor(targetReader);
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
        } catch (IOException e) {}

        return sentenceList;

    }



}
