package io.aif.language.fact;

import java.util.List;
import java.util.Set;

import io.aif.language.word.IWord;

public interface IFact {

  public List<IWord> getSemanticSentence();

  public Set<IWord> getNamedEntities();

  public boolean hasNamedEntity(final IWord properNoun);

  public default boolean hasNamedEntities(final Set<IWord> properNouns) {
    return properNouns
        .stream()
        .filter(this::hasNamedEntity)
        .count() == properNouns.size();
  }

}
