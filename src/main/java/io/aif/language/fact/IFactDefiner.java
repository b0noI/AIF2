package io.aif.language.fact;

import java.util.List;

import io.aif.language.word.IWord;

public interface IFactDefiner {

  // TODO Why can't we omit IWord
  public boolean isFact(final List<IWord> semanticSentence);

  public enum Type {
    // TODO declare a default value for this in settings
    SIMPLE_FACT(new FactDefiner()),
    // TODO declare 2 in settings
    SUPER_FACT(new FactDefiner(2));

    private final IFactDefiner instance;

    private Type(final IFactDefiner instance) {
      this.instance = instance;
    }

    public IFactDefiner getInstance() {
      return instance;
    }
  }
}
