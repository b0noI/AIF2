package io.aif.language.sentence.separators.extractors;

import com.google.common.annotations.VisibleForTesting;
import io.aif.language.common.IExtractor;
import io.aif.language.common.VisibilityReducedForCLI;
import io.aif.language.common.settings.ISettings;
import io.aif.language.token.TokenMappers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO(#261): Reformat Java code in StatSeparatorExtractor according to a Google Java Code
// guideline.
// TODO(#262): Settings should be injected in StatSeparatorExtractor.
// TODO(#263): StatSeparatorExtractor should be documented.
// TODO(#265): Publish article about the algorithm of separators extractors.
class StatSeparatorExtractor implements ISeparatorExtractor {

    private static final IExtractor<String, Character> END_CHARACTER_EXTRACTOR
            = token -> Optional.of(token.charAt(token.length() - 1));

    private static final IExtractor<String, Character> CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR
            = token -> Optional.of(token.charAt(token.length() - 2));

    private static final IExtractor<String, Character> START_CHARACTER_EXTRACTOR
            = token -> Optional.of(token.charAt(0));

    private static final IExtractor<String, Character> CHARACTER_AFTER_START_CHARACTER_EXTRACTOR
            = token -> Optional.of(token.charAt(1));

    private static final StatDataExtractor END_CHARACTER_STAT_DATA_EXTRACTOR
            = new StatDataExtractor(
                    END_CHARACTER_EXTRACTOR,
                    CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR);

    private static final StatDataExtractor START_CHARACTER_STAT_DATA_EXTRACTOR
            = new StatDataExtractor(
                    START_CHARACTER_EXTRACTOR,
                    CHARACTER_AFTER_START_CHARACTER_EXTRACTOR);

    private static final ISettings SETTINGS
            = ISettings.SETTINGS;

    @Override
    public Optional<List<Character>> extract(final List<String> tokens) {
        return Optional.of(getCharacters(tokens));
    }

    // TODO(#264) CLI should not use StatSeparatorExtractor.getCharacters, the method should be
    // private.
    @VisibilityReducedForCLI
    List<Character> getCharacters(final List<String> tokens) {
        final List<String> filteredTokens = filter(tokens);

        final StatData endCharactersStatData
                = END_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);
        final StatData startCharactersStatData
                = START_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);

        final List<CharacterStat> characterStats = getNormalizedCharactersStatistic(
                startCharactersStatData, endCharactersStatData);
        final List<CharacterStat> filteredCharactersStats
                = filterTillFirsCharacter(characterStats);
        return convertCharacterStatToCharacters(filteredCharactersStats);
    }

    List<CharacterStat> filterTillFirsCharacter(final List<CharacterStat> characterStats) {
        final OptionalInt firstIndex = IntStream
                .range(0, characterStats.size())
                .filter(i ->
                        Character
                                .isAlphabetic(characterStats.get(i).getCharacter()))
                .findFirst();
        if (!firstIndex.isPresent()) {
            return Collections.emptyList();
        }
        return characterStats.subList(0, firstIndex.getAsInt());
    }

    List<Character> convertCharacterStatToCharacters(final List<CharacterStat> charactersStats) {
        return charactersStats.stream()
                .map(CharacterStat::getCharacter)
                .collect(Collectors.toList());
    }

    @VisibleForTesting
    List<CharacterStat> getNormalizedCharactersStatistic(final StatData startCharacterStatData,
                                                         final StatData endCharactersStatData) {
        final Set<Character> allCharacters = new HashSet<>(startCharacterStatData.getAllCharacters());
        allCharacters.addAll(endCharactersStatData.getAllCharacters());
        final List<CharacterStat> characterStats =
                allCharacters
                        .stream()
                        .map(ch -> {
                            final double probability1 = startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter(ch);
                            final double probability2 = endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter(ch);
                            return new CharacterStat(ch, Math.max(probability1, probability2));
                        }).collect(Collectors.toList());

        Collections.sort(characterStats);
        return characterStats;
    }

    @VisibleForTesting
    List<String> filter(final List<String> tokens) {
        return tokens.parallelStream()
                .map(String::toLowerCase)
                .map(TokenMappers::removeMultipleEndCharacters)
                .filter(token -> token.length() > SETTINGS.minimalValuableTokenSizeForSentenceSplit())
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
