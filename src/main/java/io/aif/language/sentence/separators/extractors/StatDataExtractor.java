package io.aif.language.sentence.separators.extractors;

import com.google.common.annotations.VisibleForTesting;

import java.util.List;
import java.util.Optional;

import io.aif.language.common.IExtractor;

class StatDataExtractor {

  private final IExtractor<String, Character> edgeCharacterExtractor;

  private final IExtractor<String, Character> characterNearEdgeCharacterExtractor;

  StatDataExtractor(final IExtractor<String, Character> edgeCharacterExtractor,
                    final IExtractor<String, Character> characterNearEdgeCharacterExtractor) {
    this.edgeCharacterExtractor = edgeCharacterExtractor;
    this.characterNearEdgeCharacterExtractor = characterNearEdgeCharacterExtractor;
  }

  public StatData parseStat(final List<String> tokens) {
    final StatData endCharacterStatData = new StatData();
    tokens.parallelStream()
        .filter(token -> token.length() > 2)
        .forEach(token -> parsToken(token, endCharacterStatData));
    return endCharacterStatData;
  }

  @VisibleForTesting
  void parsToken(final String token, final StatData statData) {
    token.chars().forEach(ch -> statData.addCharacter((char) ch));

    final Optional<Character> edgeCharacter = edgeCharacterExtractor.extract(token);
    final Optional<Character> characterNearEdge =
        characterNearEdgeCharacterExtractor.extract(token);

    if (!edgeCharacter.isPresent() || !characterNearEdge.isPresent()) {
      return;
    }

    statData.addEdgeCharacter(characterNearEdge.get(), edgeCharacter.get());
  }

}
