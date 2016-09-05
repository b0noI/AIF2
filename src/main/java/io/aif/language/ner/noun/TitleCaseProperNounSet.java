package io.aif.language.ner.noun;

import java.util.Set;

import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.language.common.StringHelper;
import io.aif.language.word.IWord;

class TitleCaseProperNounSet implements IProperNounSet {

  @Override
  public FuzzyBoolean contains(final IWord word) {
    final Set<String> tokens = word.getAllTokens();

    // WARN! This is to handle the zero length case and avoid division by zero at the bottom
    if (tokens.size() == 0)
      return new FuzzyBoolean(0d);

    long numTokensUpperCase = tokens
        .stream()
        .filter(StringHelper::startsWithUpperCase)
        .count();
    return new FuzzyBoolean((double) numTokensUpperCase / tokens.size());
  }

}
