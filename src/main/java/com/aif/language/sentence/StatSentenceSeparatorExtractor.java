package com.aif.language.sentence;

import com.aif.language.common.IExtractor;

import java.util.*;
import java.util.stream.Collectors;

class StatSentenceSeparatorExtractor implements ISentenceSeparatorExtractor {

    private final static double                         PROBABILITY_LIMIT_REDUCER                   = 2.;

    private final static IExtractor<String, Character>  END_CHARACTER_EXTRACTOR                     = token -> Optional.of(token.charAt(token.length() - 1));

    private final static IExtractor<String, Character>  CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR    = token -> Optional.of(token.charAt(token.length() - 2));

    private final static IExtractor<String, Character>  START_CHARACTER_EXTRACTOR                   = token -> Optional.of(token.charAt(0));

    private final static IExtractor<String, Character>  CHARACTER_AFTER_START_CHARACTER_EXTRACTOR   = token -> Optional.of(token.charAt(1));

    private final static StatDataExtractor              END_CHARACTER_STAT_DATA_EXTRACTOR           = new StatDataExtractor(END_CHARACTER_EXTRACTOR, CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR);

    private final static StatDataExtractor              START_CHARACTER_STAT_DATA_EXTRACTOR         = new StatDataExtractor(START_CHARACTER_EXTRACTOR, CHARACTER_AFTER_START_CHARACTER_EXTRACTOR);

    @Override
    public Optional<List<Character>> extract(final List<String> tokens) {

        final List<String> filteredTokens = filter(tokens);

        final StatData endCharactersStatData = END_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);
        final StatData startCharactersStatData = START_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);

        final List<CharacterStat> characterStats = getCharactersStatistic(startCharactersStatData, endCharactersStatData);

        final List<Character> filteredCharactersStat = filterCharacterStatisticFromNonEndCharacters(characterStats)
                .stream()
                .map(CharacterStat::getCharacter)
                .collect(Collectors.toList());
        return Optional.of(filteredCharactersStat);
    }

    private List<CharacterStat> filterCharacterStatisticFromNonEndCharacters(final List<CharacterStat> characterStats) {
        final List<Double> deltas = new ArrayList<>(characterStats.size() - 1);
        for (int i = 1; i < characterStats.size(); i++) {
            final CharacterStat left = characterStats.get(i - 1);
            final CharacterStat right = characterStats.get(i);
            deltas.add(left.getProbabilityThatEndCharacter() - right.getProbabilityThatEndCharacter());
        }
        final Optional<Double> maxDelta = deltas
                .stream()
                .max(Double::compareTo);

        if (!maxDelta.isPresent()) {
            return Arrays.asList(new CharacterStat[0]);
        }

        final OptionalInt index = deltas
                .stream()
                .filter(delta -> delta > (maxDelta.get() / PROBABILITY_LIMIT_REDUCER))
                .mapToInt(delta -> deltas.indexOf(delta)).max();

        if (!index.isPresent()) {
            return Arrays.asList(new CharacterStat[0]);
        }

        return characterStats.subList(0, index.getAsInt() + 1);
    }

    private List<CharacterStat> getCharactersStatistic(final StatData startCharacterStatData, final StatData endCharactersStatData) {
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

    private static List<String> filter(final List<String> tokens) {
        return tokens.parallelStream()
                .map(String::toLowerCase).map(token -> {
                    int index = token.length();
                    while (index > 1 &&
                            token.charAt(index - 1) == token.charAt(index - 2)) {
                        index--;
                    }
                    if (index != token.length()) {
                        return token.substring(0, index);
                    }
                    return token;
                })
                .collect(Collectors.toList());
    }

    private static class CharacterStat implements Comparable<CharacterStat> {

        private final Character character;

        private final Double probabilityThatEndCharacter;

        private CharacterStat(Character character, Double probabilityThatEndCharacter) {
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
        public int compareTo(CharacterStat that) {
            return that.getProbabilityThatEndCharacter().compareTo(this.getProbabilityThatEndCharacter());
        }
    }

}
