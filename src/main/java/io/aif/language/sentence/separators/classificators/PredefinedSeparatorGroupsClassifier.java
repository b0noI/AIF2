package io.aif.language.sentence.separators.classificators;

import com.google.common.collect.ImmutableMap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class PredefinedSeparatorGroupsClassifier implements ISeparatorGroupsClassifier {

  private static final char DOT_CHARACTER = '.';

  @Override
  public Map<Group, Set<Character>> classify(List<String> tokens,
                                             List<Set<Character>> separatorsGroups) {
    final Set<Character> group1 = new HashSet<>();
    final Set<Character> group2 = new HashSet<>();

    separatorsGroups.forEach(separators -> {
      if (separators.contains(DOT_CHARACTER)) {
        group1.addAll(separators);
      } else {
        group2.addAll(separators);
      }
    });
    return ImmutableMap.of(Group.GROUP_1, group1, Group.GROUP_2, group2);
  }
}
