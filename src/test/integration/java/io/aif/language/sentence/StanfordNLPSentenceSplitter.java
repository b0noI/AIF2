package io.aif.language.sentence;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import io.aif.language.common.ISplitter;

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
          if (sentenceSb.length() > 1) {
            sentenceSb.append(" ");
          }
          sentenceSb.append(token);
        }
        sentenceList.add(sentenceSb.toString());
      }
    } catch (IOException e) {
    }

    return sentenceList;

  }


}
