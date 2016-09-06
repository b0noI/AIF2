package io.aif.language.ner;

import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.fuzzy.set.IFuzzySet;
import io.aif.language.ner.noun.IProperNounSet;
import io.aif.language.ner.numeral.SimpleNumeralSet;
import io.aif.language.word.IWord;

public enum Type {

  NOUN(IProperNounSet.getDefault()),
  NUMERAL(new SimpleNumeralSet());

  private final IFuzzySet<IWord> set;

  Type(final IFuzzySet<IWord> set) {
    this.set = set;
  }

  public FuzzyBoolean contains(final IWord word) {
    return set.contains(word);
  }

}
