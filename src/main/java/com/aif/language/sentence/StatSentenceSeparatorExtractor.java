package com.aif.language.sentence;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class StatSentenceSeparatorExtractor implements ISentenceSeparatorExtractor {

    private final static double PROBABILITY_LIMIT_REDUCER = 2.;

    @Override
    public Optional<List<Character>> extract(final List<String> tokens) {
        final StatData statData = StatSentenceSeparatorExtractor.parseStat(tokens);
        final List<CharacterStat> characterStats = getCharactersStatistic(statData);

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

    private List<CharacterStat> getCharactersStatistic(final StatData statData) {
        final List<CharacterStat> characterStats = new ArrayList<>(statData.getAllCharacters().size());
        for (Character ch : statData.getAllCharacters()) {
            final CharacterStat characterStat = new CharacterStat(ch, statData.getProbabilityThatCharacterIsSplitterCharacter(ch));
            characterStats.add(characterStat);
        }
        Collections.sort(characterStats);
        return characterStats;
    }

    private static StatData parseStat(final List<String> tokens) {
        final StatData statData = new StatData();
        tokens.parallelStream().filter(token -> token.length() > 2).forEach(token -> {
            token.chars().forEach(ch -> statData.addCharacter((char) ch));
            statData.addEndCharacter(token.charAt(token.length() - 2),
                    token.charAt(token.length() - 1));
        });
        return statData;
    }

    private static class StatData {

        private final Map<Character, Map<Character, Integer>>   charactersBeforeEndCharacter  = new ConcurrentHashMap<>();

        private final Map<Character, Integer>                   endCharacters                 = new ConcurrentHashMap<>();

        private final Map<Character, Integer>                   characters                    = new ConcurrentHashMap<>();

        public void addEndCharacter(final Character characterBeforeEndCharacterm, final Character endCharacter) {
            final Character lowCaseCharacterBeforeEndCharacter = StatData.prepareCharacter(characterBeforeEndCharacterm);
            final Character lowCaseEndCharacter = StatData.prepareCharacter(endCharacter);

            endCharacters.merge(lowCaseEndCharacter, 1, (v1, v2) -> v1 + v2);

            final Map<Character, Integer> characterBeforeEndCharacterMap = getMapForEndCharacter(endCharacter);
            characterBeforeEndCharacterMap.merge(lowCaseCharacterBeforeEndCharacter, 1, (v1, v2) -> v1 + v2);
        }

        public void addCharacter(final Character ch) {
            final Character lowCaseCharacter = StatData.prepareCharacter(ch);
            characters.merge(lowCaseCharacter, 1, (v1, v2) -> v1 + v2);
        }

        public Set<Character> getAllCharacters() {
            return characters.keySet();
        }

        public double getProbabilityThatCharacterIsSplitterCharacter(final Character ch) {
            return getProbabiltyThatCharacterInTheEnd(ch) * getProbablityThatCharacterBeforeIsEndCharacter(ch);
        }

        private Map<Character, Integer> getMapForEndCharacter(final Character endCharacter) {
            if (!charactersBeforeEndCharacter.containsKey(endCharacter)) {
                synchronized (charactersBeforeEndCharacter) {
                    final Map<Character, Integer> targetMap = charactersBeforeEndCharacter.getOrDefault(endCharacter, new ConcurrentHashMap<Character, Integer>());
                    charactersBeforeEndCharacter.put(endCharacter, targetMap);
                    return targetMap;
                }
            }
            return charactersBeforeEndCharacter.getOrDefault(endCharacter, new ConcurrentHashMap<>());
        }

        private double getProbabiltyThatCharacterInTheEnd(final Character ch) {
            final Character lowCaseCharacter = StatData.prepareCharacter(ch);
            return (double) endCharacters.getOrDefault(lowCaseCharacter, 0) /
                    (double) characters.getOrDefault(lowCaseCharacter, 0);
        }

        private double getProbablityThatCharacterBeforeIsEndCharacter(final Character ch) {
            final Character lowCaseCharacter = StatData.prepareCharacter(ch);
            final Map<Character, Integer> beforeCharacters = charactersBeforeEndCharacter.getOrDefault(lowCaseCharacter, new HashMap<>());

            return beforeCharacters.keySet()
                    .stream()
                    .mapToDouble(k -> getProbabiltyThatCharacterInTheEnd(k) * (double)beforeCharacters.get(k))
                    .sum() / (double)endCharacters.getOrDefault(lowCaseCharacter, 1);
        }

        private static Character prepareCharacter(final Character ch) {
            return Character.toLowerCase(ch);
        }

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
