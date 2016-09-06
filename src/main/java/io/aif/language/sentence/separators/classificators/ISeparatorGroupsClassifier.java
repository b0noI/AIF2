package io.aif.language.sentence.separators.classificators;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISeparatorGroupsClassifier {

  public Map<Group, Set<Character>> classify(final List<String> tokens,
                                             final List<Set<Character>> separatorsGroups);

  public enum Group {

    GROUP_1,
    GROUP_2

  }

  public enum Type {

    PREDEFINED(new PredefinedSeparatorGroupsClassifier()),
    PROBABILITY(new StatSeparatorGroupsClassifier());

    private final ISeparatorGroupsClassifier instance;

    Type(ISeparatorGroupsClassifier instance) {
      this.instance = instance;
    }

    public ISeparatorGroupsClassifier getInstance() {
      return instance;
    }

  }

}
