package io.aif.language.sentence.separators.groupers;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.ImmutableList.of;

class PredefinedGrouper implements ISeparatorsGrouper {

  private static final List<Character> GROUP_1_CHARACTERS = of('.', '!', '?');

  @Override
  public List<Set<Character>> group(List<String> tokens, List<Character> splitters) {
    final Set<Character> group1 = new HashSet<>();
    final Set<Character> group2 = new HashSet<>();

    splitters.forEach(ch -> {
      if (GROUP_1_CHARACTERS.contains(ch)) {
        group1.add(ch);
      } else {
        group2.add(ch);
      }
    });
    return of(group1, group2);
  }

}
