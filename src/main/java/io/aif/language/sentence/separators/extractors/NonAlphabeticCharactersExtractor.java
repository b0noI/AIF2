package io.aif.language.sentence.separators.extractors;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

class NonAlphabeticCharactersExtractor implements ISeparatorExtractor {

  @Override
  public Optional<List<Character>> extract(final List<String> from) {
    final Set<Character> result = new HashSet<>();
    from.forEach(token -> {
      for (Character ch : token.toCharArray())
        if (!Character.isLetterOrDigit(ch)) result.add(ch);
    });
    return Optional.of(new ArrayList<>(result));
  }

}
