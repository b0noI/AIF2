package io.aif.language.ner;

import java.util.Optional;

import io.aif.language.word.IWord;

public class NERExtractor {

  public Optional<Type> getNerType(final IWord word) {
    for (Type nerType : Type.values()) {
      if (nerType.contains(word).isTrue()) {
        return Optional.of(nerType);
      }
    }
    return Optional.empty();
  }

}
