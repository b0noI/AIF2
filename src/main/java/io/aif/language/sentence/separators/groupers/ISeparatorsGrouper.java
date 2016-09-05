package io.aif.language.sentence.separators.groupers;

import java.util.List;
import java.util.Set;

public interface ISeparatorsGrouper {

  public List<Set<Character>> group(final List<String> tokens, final List<Character> splitters);

  public enum Type {

    PREDEFINED(new PredefinedGrouper()),
    PROBABILITY(new StatGrouper());

    private final ISeparatorsGrouper instance;

    Type(ISeparatorsGrouper instance) {
      this.instance = instance;
    }

    public ISeparatorsGrouper getInstance() {
      return instance;
    }

  }

}
