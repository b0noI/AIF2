package io.aif.language.fact;

import java.util.List;
import java.util.Set;

import io.aif.language.word.IWord;

public interface IFactQuery {

  public List<List<IFact>> findPath(final IWord properNoun1, final IWord properNoun2);

  public Set<IFact> allFacts();

}
