package io.aif.language.ner.noun;

import java.util.Set;

import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.language.common.StringHelper;
import io.aif.language.word.IWord;

class TitleCaseProperNounSet implements IProperNounSet {

  @Override
  public FuzzyBoolean contains(final IWord word) {
    final Set<String> tokens = word.getAllTokens();
    long count = tokens.stream()
                       .filter(StringHelper::startsWithUpperCase)
                       .count();
    return count == 0 ? new FuzzyBoolean(0d) : new FuzzyBoolean((double) count / tokens.size());
  }

}
