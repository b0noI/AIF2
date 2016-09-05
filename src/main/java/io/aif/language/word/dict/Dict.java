package io.aif.language.word.dict;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import io.aif.language.common.IDict;
import io.aif.language.common.ISearchable;
import io.aif.language.word.IWord;

class Dict implements IDict<IWord>, ISearchable<String, IWord> {

  private final Set<IWord> words;

  private final Map<String, IWord> reverseIndex;

  private Dict(final Set<IWord> words, Map<String, IWord> reverseIndex) {
    this.words = words;
    this.reverseIndex = reverseIndex;
  }

  private static Map<String, IWord> generateReverseIndex(final Set<IWord> words) {
    //TODO: Opportunities for parallelization?
    final Map<String, IWord> reverseIndex = new HashMap<>();
    for (IWord word : words) {
      word.getAllTokens().forEach(token -> reverseIndex.put(token, word));
    }
    return reverseIndex;
  }

  public static Dict create(final Set<IWord> words) {
    final Dict d = new Dict(words, generateReverseIndex(words));
    return d;
  }

  @Override
  public Set<IWord> getWords() {
    return words;
  }

  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder();
    getWords()
        .stream()
        .sorted((w1, w2) -> w1.getRootToken().compareTo(w2.getRootToken()))
        .forEach(word -> stringBuilder.append(String.format("%s\n", word.toString())));
    return stringBuilder.toString();
  }

  @Override
  public Optional<IWord> search(final String token) {
    return reverseIndex.containsKey(token) ?
        Optional.of(reverseIndex.get(token)) : Optional.empty();
  }

}
