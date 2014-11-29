package io.aif.language.sentence.separators.extractors;

import com.google.common.annotations.VisibleForTesting;
import io.aif.language.common.IExtractor;
import io.aif.language.common.VisibilityReducedForCLI;
import io.aif.language.token.TokenMappers;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.*;
import java.util.stream.Collectors;

class StatSeparatorExtractor implements ISeparatorExtractor {

    private static final IExtractor<String, Character>  END_CHARACTER_EXTRACTOR                     = token -> Optional.of(token.charAt(token.length() - 1));

    private static final IExtractor<String, Character>  CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR    = token -> Optional.of(token.charAt(token.length() - 2));

    private static final IExtractor<String, Character>  START_CHARACTER_EXTRACTOR                   = token -> Optional.of(token.charAt(0));

    private static final IExtractor<String, Character>  CHARACTER_AFTER_START_CHARACTER_EXTRACTOR   = token -> Optional.of(token.charAt(1));

    private static final StatDataExtractor              END_CHARACTER_STAT_DATA_EXTRACTOR           = new StatDataExtractor(END_CHARACTER_EXTRACTOR, CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR);

    private static final StatDataExtractor              START_CHARACTER_STAT_DATA_EXTRACTOR         = new StatDataExtractor(START_CHARACTER_EXTRACTOR, CHARACTER_AFTER_START_CHARACTER_EXTRACTOR);

    private static final int                            MINIMUM_TOKEN_SIZE                          = 3;

    @Override
    public Optional<List<Character>> extract(final List<String> tokens) {
        return Optional.of(getCharacters(tokens));
    }

    @VisibilityReducedForCLI
    List<Character> getCharacters(final List<String> tokens) {
        final List<String> filteredTokens = filter(tokens);

        final StatData endCharactersStatData = END_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);
        final StatData startCharactersStatData = START_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);

        return convertCharacterStatToCharacters(getCharactersStatistic(startCharactersStatData, endCharactersStatData), tokens, endCharactersStatData);
    }

    List<Character> convertCharacterStatToCharacters(final List<CharacterStat> charactersStats, final List<String> tokens, final StatData endCharactersStatData) {
        return postFilter(charactersStats.stream()
                .map(CharacterStat::getCharacter)
                .collect(Collectors.toList()), endCharactersStatData);
    }

    @VisibleForTesting
    List<CharacterStat> filterCharacterStatisticFromNonEndCharacters(final List<CharacterStat> characterStats) {
        final SummaryStatistics stats = createSummaryStatistics();
        characterStats
                .stream()
                .mapToDouble(CharacterStat::getProbabilityThatEndCharacter)
                .forEach(item -> stats.addValue(item));

        final List<CharacterStat> filteredCharacterStats = characterStats
                .stream()
                .sorted((i1, i2) -> i1.getProbabilityThatEndCharacter().compareTo(i2.getProbabilityThatEndCharacter()))
                .collect(Collectors.toList())
                .subList((int) ((double) characterStats.size() * .7), characterStats.size());
        return filteredCharacterStats;
    }

    @VisibleForTesting
    List<CharacterStat> getCharactersStatistic(final StatData startCharacterStatData, final StatData endCharactersStatData) {
        final List<CharacterStat> characterStats = new ArrayList<>(startCharacterStatData.getAllCharacters().size());
        for (Character ch : startCharacterStatData.getAllCharacters()) {
            final double probability1 = startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter(ch);
            final double probability2 = endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter(ch);
            final CharacterStat characterStat = new CharacterStat(ch, Collections.max(Arrays.asList(probability1, probability2)));
            characterStats.add(characterStat);
        }
        Collections.sort(characterStats);
        return characterStats;
    }

    @VisibleForTesting
    List<String> filter(final List<String> tokens) {
        return tokens.parallelStream()
                .map(String::toLowerCase).map(TokenMappers::removeMultipleEndCharacters)
                .filter(token -> token.length() > MINIMUM_TOKEN_SIZE)
                .collect(Collectors.toList());
    }

    @VisibleForTesting
    SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    @VisibleForTesting
    List<Character> postFilter(final List<Character> separators, final StatData endCharactersStatData ) {

        final List<Character> result = separators
                .stream()
                .filter(splitor -> endCharactersStatData.getProbabilityThatCharacterOnEdge(splitor) > .65)
                .filter(ch -> endCharactersStatData.getCharacterCount(ch) > 10)
                .collect(Collectors.toList());

        return result;
    }

    @VisibilityReducedForCLI
    static class CharacterStat implements Comparable<CharacterStat> {

        private final Character character;

        private final Double probabilityThatEndCharacter;

        public CharacterStat(Character character, Double probabilityThatEndCharacter) {
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
