package io.aif.language.sentence;

import java.util.List;

import io.aif.language.common.ISplitter;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.Tokenizer;

class AIF2NLPSentenceSplitter implements ISplitter<String, List<String>> {

  private final Tokenizer tokenizer = new Tokenizer();
  private final AbstractSentenceSplitter sentenceSplitter =
      AbstractSentenceSplitter.Type.HEURISTIC.getInstance();

  public List<List<String>> split(String target) {

    List<String> tokens = tokenizer.split(target);
    List<List<String>> sentenceList = sentenceSplitter.split(tokens);

    return sentenceList;

  }

}
