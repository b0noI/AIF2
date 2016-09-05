package io.aif.language.token.separator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import io.aif.language.common.settings.ISettings;

import static java.util.stream.Collectors.toList;

class PredefinedTokenSeparatorExtractor implements ITokenSeparatorExtractor {

  private final List<Character> separators;

  PredefinedTokenSeparatorExtractor(ISettings settings) {
    this.separators = Stream.of(settings.predefinedSeparators().split(""))
        .map(it -> it.charAt(0)).collect(toList());
  }

  @Override
  public Optional<List<Character>> extract(final String txt) {
    return Optional.of(separators);
  }
}
