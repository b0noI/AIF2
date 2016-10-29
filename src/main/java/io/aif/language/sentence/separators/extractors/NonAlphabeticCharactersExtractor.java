package io.aif.language.sentence.separators.extractors;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class NonAlphabeticCharactersExtractor implements ISeparatorExtractor {

  @Override
  public Optional<List<Character>> extract(final List<String> from) {
    return Optional.of(from.stream()
      .flatMap(token -> token.chars().boxed())
      .filter(cp -> !Character.isLetterOrDigit(cp))
      .map(cp -> (char) cp.intValue())
      .collect(Collectors.toList()));
  }

}
