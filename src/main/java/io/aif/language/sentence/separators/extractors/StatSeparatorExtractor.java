package io.aif.language.sentence.separators.extractors;

import com.google.common.annotations.VisibleForTesting;
import io.aif.language.common.IExtractor;
import io.aif.language.common.VisibilityReducedForCLI;
import io.aif.language.common.settings.ISettings;
import io.aif.language.token.TokenMappers;

import java.util.*;
import java.util.stream.Collectors;

class StatSeparatorExtractor implements ISeparatorExtractor {

    private static final IExtractor<String, Character>  END_CHARACTER_EXTRACTOR                     = token -> Optional.of(token.charAt(token.length() - 1));

    private static final IExtractor<String, Character>  CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR    = token -> Optional.of(token.charAt(token.length() - 2));

    private static final IExtractor<String, Character>  START_CHARACTER_EXTRACTOR                   = token -> Optional.of(token.charAt(0));

    private static final IExtractor<String, Character>  CHARACTER_AFTER_START_CHARACTER_EXTRACTOR   = token -> Optional.of(token.charAt(1));

    private static final StatDataExtractor              END_CHARACTER_STAT_DATA_EXTRACTOR           = new StatDataExtractor(END_CHARACTER_EXTRACTOR, CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR);

    private static final StatDataExtractor              START_CHARACTER_STAT_DATA_EXTRACTOR         = new StatDataExtractor(START_CHARACTER_EXTRACTOR, CHARACTER_AFTER_START_CHARACTER_EXTRACTOR);

    private static final ISettings                      SETTINGS                                    = ISettings.SETTINGS;

    @Override
    public Optional<List<Character>> extract(final List<String> tokens) {
        return Optional.of(getCharacters(tokens));
    }

    @VisibilityReducedForCLI
    List<Character> getCharacters(final List<String> tokens) {
        final List<String> filteredTokens = filter(tokens);

        final StatData endCharactersStatData = END_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);
        final StatData startCharactersStatData = START_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);

        final List<CharacterStat> characterStats = getNormalizedCharactersStatistic(startCharactersStatData, endCharactersStatData);
        final List<CharacterStat> characterStatsAfterFirstFilter = firstFilter(characterStats, endCharactersStatData);
        final List<CharacterStat> characterStatsAfterSecondFilter = secondFilter(characterStatsAfterFirstFilter, endCharactersStatData);
        return convertCharacterStatToCharacters(characterStatsAfterSecondFilter);
    }

    List<Character> convertCharacterStatToCharacters(final List<CharacterStat> charactersStats) {
        return charactersStats.stream()
                .map(CharacterStat::getCharacter)
                .collect(Collectors.toList());
    }

    @VisibleForTesting
    List<CharacterStat> firstFilter(final List<CharacterStat> characterStats,
                                    final StatData endCharactersStatData) {
        final List<CharacterStat> filteredCharacterStats = characterStats
                .stream()
                .sorted((i1, i2) -> i1.getProbabilityThatEndCharacter().compareTo(i2.getProbabilityThatEndCharacter()))
                .filter(stat -> endCharactersStatData.getProbabilityThatCharacterBeforeIsEdgeCharacter(stat.getCharacter()) >= SETTINGS.thresholdPFirstFilterForSeparatorCharacter())
                .collect(Collectors.toList());
        return filteredCharacterStats;
    }

    @VisibleForTesting
    List<CharacterStat> getNormalizedCharactersStatistic(final StatData startCharacterStatData,
                                                         final StatData endCharactersStatData) {

        final List<CharacterStat> characterStats =
                startCharacterStatData
                        .getAllCharacters()
                        .stream()
                        .map(ch -> {
                            final double probability1 = startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter(ch);
                            final double probability2 = endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter(ch);
                            return new CharacterStat(ch, Math.max(probability1, probability2));
                        }).collect(Collectors.toList());

        final List<CharacterStat> normalizedCharacterStats = normilize(characterStats);

        Collections.sort(normalizedCharacterStats);
        return normalizedCharacterStats;
    }

    private List<CharacterStat> normilize(final List<CharacterStat> characterStats) {
        final OptionalDouble maxOpt = characterStats
                .stream()
                .mapToDouble(CharacterStat::getProbabilityThatEndCharacter)
                .max();

        if (!maxOpt.isPresent()) {
            return characterStats;
        }
        final double max = maxOpt.getAsDouble();
        return characterStats
                .stream()
                .map(stat -> new CharacterStat(stat.getCharacter(), stat.getProbabilityThatEndCharacter() / max))
                .collect(Collectors.toList());
    }

    @VisibleForTesting
    List<String> filter(final List<String> tokens) {
        return tokens.parallelStream()
                .map(String::toLowerCase)
                .map(TokenMappers::removeMultipleEndCharacters)
                .distinct()
                .filter(token -> token.length() > SETTINGS.minimalValuableTokenSizeForSentenceSplit())
                .collect(Collectors.toList());
    }

    @VisibleForTesting
    List<CharacterStat> secondFilter(final List<CharacterStat> separators, final StatData endCharactersStatData) {

        return separators
                .stream()
                .filter(splitter -> endCharactersStatData.getProbabilityThatCharacterOnEdge(splitter.getCharacter()) > SETTINGS.thresholdPSecondFilterForSeparatorCharacter())
                .filter(splitter -> endCharactersStatData.getCharacterCount(splitter.getCharacter()) > SETTINGS.minimumCharacterObervationsCountForMakingCharatcerValuableDuringSentenceSplitting())
                .collect(Collectors.toList());
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

            if (!character.equals(that.character)) return false;
            if (!probabilityThatEndCharacter.equals(that.probabilityThatEndCharacter)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = character.hashCode();
            result = 31 * result + probabilityThatEndCharacter.hashCode();
            return result;
        }

    }

}
