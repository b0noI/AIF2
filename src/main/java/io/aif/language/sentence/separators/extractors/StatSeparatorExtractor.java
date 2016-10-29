package io.aif.language.sentence.separators.extractors;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Guice;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import io.aif.language.common.IExtractor;
import io.aif.language.common.VisibilityReducedForCLI;
import io.aif.language.common.settings.ISettings;
import io.aif.language.common.settings.SettingsModule;
import io.aif.language.token.TokenMappers;

import static java.lang.Character.isAlphabetic;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

// TODO(#263): StatSeparatorExtractor should be documented.
// TODO(#265): Publish article about the algorithm of separators extractors.
class StatSeparatorExtractor implements ISeparatorExtractor {

  private static final IExtractor<String, Character> END_CHARACTER_EXTRACTOR =
    token -> Optional.of(token.charAt(token.length() - 1));

  private static final IExtractor<String, Character> CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR =
    token -> Optional.of(token.charAt(token.length() - 2));

  private static final IExtractor<String, Character> START_CHARACTER_EXTRACTOR = token -> Optional.of(token.charAt(0));

  private static final IExtractor<String, Character> CHARACTER_AFTER_START_CHARACTER_EXTRACTOR =
    token -> Optional.of(token.charAt(1));

  private static final StatDataExtractor END_CHARACTER_STAT_DATA_EXTRACTOR =
    new StatDataExtractor(END_CHARACTER_EXTRACTOR, CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR);

  private static final StatDataExtractor START_CHARACTER_STAT_DATA_EXTRACTOR =
    new StatDataExtractor(START_CHARACTER_EXTRACTOR, CHARACTER_AFTER_START_CHARACTER_EXTRACTOR);

  private static final ISettings SETTINGS = Guice.createInjector(new SettingsModule()).getInstance(ISettings.class);

  @Override
  public Optional<List<Character>> extract(final List<String> tokens) {
    return Optional.of(getCharacters(tokens));
  }

  // TODO(#264) CLI should not use StatSeparatorExtractor.getCharacters, the method should be
  // private.
  @VisibilityReducedForCLI
  List<Character> getCharacters(final List<String> tokens) {
    final List<String> filteredTokens = filter(tokens);
    final StatData endCharactersStatData = END_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);
    final StatData startCharactersStatData = START_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);
    final List<CharacterStat> characterStats = getNormalizedCharactersStatistic(startCharactersStatData,
      endCharactersStatData);
    final List<CharacterStat> filteredCharactersStats = filterTillFirstCharacter(characterStats);
    return convertCharacterStatToCharacters(filteredCharactersStats);
  }

  List<CharacterStat> filterTillFirstCharacter(final List<CharacterStat> characterStats) {
    Optional<CharacterStat> firstStat = characterStats
      .stream()
      .filter(stat -> isAlphabetic(stat.getCharacter()))
      .findFirst();
    return firstStat.isPresent() ? characterStats.subList(0, characterStats.indexOf(firstStat.get())) : emptyList();
  }

  List<Character> convertCharacterStatToCharacters(final List<CharacterStat> charactersStats) {
    return charactersStats.stream()
      .map(CharacterStat::getCharacter)
      .collect(toList());
  }

  @VisibleForTesting
  List<CharacterStat> getNormalizedCharactersStatistic(final StatData startCharacterStatData,
                                                       final StatData endCharactersStatData) {
    final Set<Character> allCharacters = new HashSet<>(startCharacterStatData.getAllCharacters());
    allCharacters.addAll(endCharactersStatData.getAllCharacters());
    return allCharacters.stream()
      .map(ch -> {
        final double probability1 = startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter(ch);
        final double probability2 = endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter(ch);
        return new CharacterStat(ch, Math.max(probability1, probability2));
      })
      .sorted()
      .collect(toList());
  }

  @VisibleForTesting
  List<String> filter(final List<String> tokens) {
    return tokens.parallelStream()
      .map(String::toLowerCase)
      .map(TokenMappers::removeMultipleEndCharacters)
      .filter(token -> token.length() > SETTINGS.minimalValuableTokenSizeForSentenceSplit())
      .collect(toList());
  }

  @VisibilityReducedForCLI
  static class CharacterStat implements Comparable<CharacterStat> {

    private final Character character;

    private final Double probabilityThatEndCharacter;

    public CharacterStat(final Character character,
                         final Double probabilityThatEndCharacter) {
      this.character = character;
      this.probabilityThatEndCharacter = probabilityThatEndCharacter;
    }

    public Character getCharacter() {
      return character;
    }

    public Double getProbabilityThatEndCharacter() {
      return probabilityThatEndCharacter;
    }

    @Override
    public int compareTo(final CharacterStat that) {
      return that.getProbabilityThatEndCharacter().compareTo(this.getProbabilityThatEndCharacter());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      CharacterStat that = (CharacterStat) o;

      return character.equals(that.character) && probabilityThatEndCharacter.equals(that.probabilityThatEndCharacter);
    }

    @Override
    public int hashCode() {
      int result = character.hashCode();
      result = 31 * result + probabilityThatEndCharacter.hashCode();
      return result;
    }

  }

}
