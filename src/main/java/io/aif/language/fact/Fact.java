package io.aif.language.fact;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.aif.language.word.IWord;

class Fact implements IFact {

  private final List<IWord> semanticSentence;

  private final Set<IWord> namedEntities;

  public Fact(final List<IWord> semanticSentence, final Set<IWord> namedEntities) {
    this.semanticSentence = semanticSentence;
    this.namedEntities = namedEntities;
  }

  public List<IWord> getSemanticSentence() {
    return semanticSentence;
  }

  public Set<IWord> getNamedEntities() {
    //TODO The assumption here is that a sentence without a proper noun is not a fact.
    return namedEntities;
  }

  public boolean hasNamedEntity(final IWord properNoun) {
    return getNamedEntities().contains(properNoun);
  }

  public String toString() {
    return semanticSentence.stream().map(IWord::toString).collect(Collectors.joining());
  }

}
