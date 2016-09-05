package io.aif.language.word.dict;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.aif.language.common.IGrouper;
import io.aif.language.word.comparator.IGroupComparator;

public class FormGrouper implements IGrouper {

  private final IGroupComparator groupComparator;

  FormGrouper(final IGroupComparator groupComparator) {
    this.groupComparator = groupComparator;
  }

  @Override
  public List<Set<String>> group(final Collection<String> tokens) {
    List<Set<String>> tokenSets = tokens
        .stream()
        .map(token -> new HashSet<String>() {{
          add(token);
        }})
        .collect(Collectors.toList());

    final WordSetDict wordSetDict = new WordSetDict(groupComparator);
    tokenSets.stream().forEach(wordSetDict::mergeSet);
    return wordSetDict.getTokens();
  }
}
