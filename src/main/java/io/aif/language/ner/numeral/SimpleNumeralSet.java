package io.aif.language.ner.numeral;

import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.fuzzy.set.IFuzzySet;
import io.aif.language.word.IWord;

public class SimpleNumeralSet implements IFuzzySet<IWord> {

  @Override
  public FuzzyBoolean contains(final IWord element) {
    final long count = element.getAllTokens().stream().map(this::isNumeral).count();
    return new FuzzyBoolean((double) count / (double) element.getAllTokens().size());
  }

  private FuzzyBoolean isNumeral(final String token) {
    final long count = token.chars()
        .mapToObj(ch -> (char) ch)
        .filter(Character::isDigit)
        .count();
    return new FuzzyBoolean((double) count / (double) token.length());
  }

}
