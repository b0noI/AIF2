package com.aif.language.sentence;

import com.aif.language.common.IExtractor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class StatSentenceSeparatorExtractor implements ISentenceSeparatorExtractor {

    private final static double PROBABILITY_LIMIT_REDUCER = 2.;

    @Override
    public Optional<List<Character>> extract(final List<String> tokens) {

        final IExtractor<String, Character> extractEndCharacter = token -> Optional.of(token.charAt(token.length() - 1));
        final IExtractor<String, Character> extractCharacterBeforeEndCharacter = token -> Optional.of(token.charAt(token.length() - 2));
        final IExtractor<String, Character> extractStartCharacter = token -> Optional.of(token.charAt(0));
        final IExtractor<String, Character> extractCharacterAfterStartCharacter = token -> Optional.of(token.charAt(1));

        final StatData endCharactersStatData = new StatData(extractEndCharacter, extractCharacterBeforeEndCharacter).parseStat(tokens);
        final StatData startCharactersStatData = new StatData(extractStartCharacter, extractCharacterAfterStartCharacter).parseStat(tokens);

        final List<CharacterStat> characterStats = getCharactersStatistic(startCharactersStatData, endCharactersStatData);

        final OptionalDouble maxProb = characterStats
                .stream()
                .mapToDouble(CharacterStat::getProbabilityThatEndCharacter)
                .max();

        if (!maxProb.isPresent()) {
            return Optional.of(Arrays.asList(new Character[0]));
        }

        characterStats.stream().mapToDouble(CharacterStat::getProbabilityThatEndCharacter).forEach(System.out::println);

        final List<Character> filteredCharactersStat = filterCharacterStatisticFromNonEndCharacters(characterStats)
                .stream()
                .<Character>map(CharacterStat::getCharacter)
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
