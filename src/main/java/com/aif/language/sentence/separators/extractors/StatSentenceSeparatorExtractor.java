package com.aif.language.sentence.separators.extractors;

import com.aif.language.common.IExtractor;
import com.aif.language.common.VisibilityReducedForCLI;
import com.aif.language.common.VisibilityReducedForTestPurposeOnly;
import com.aif.language.token.TokenMappers;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.*;
import java.util.stream.Collectors;

class StatSentenceSeparatorExtractor implements ISentenceSeparatorExtractor {

    private static final IExtractor<String, Character>  END_CHARACTER_EXTRACTOR                     = token -> Optional.of(token.charAt(token.length() - 1));

    private static final IExtractor<String, Character>  CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR    = token -> Optional.of(token.charAt(token.length() - 2));

    private static final IExtractor<String, Character>  START_CHARACTER_EXTRACTOR                   = token -> Optional.of(token.charAt(0));

    private static final IExtractor<String, Character>  CHARACTER_AFTER_START_CHARACTER_EXTRACTOR   = token -> Optional.of(token.charAt(1));

    private static final StatDataExtractor              END_CHARACTER_STAT_DATA_EXTRACTOR           = new StatDataExtractor(END_CHARACTER_EXTRACTOR, CHARACTER_BEFORE_END_CHARACTER_EXTRACTOR);

    private static final StatDataExtractor              START_CHARACTER_STAT_DATA_EXTRACTOR         = new StatDataExtractor(START_CHARACTER_EXTRACTOR, CHARACTER_AFTER_START_CHARACTER_EXTRACTOR);

    private static final int                            MINIMUM_TOKEN_SIZE                          = 3;

    @Override
    public Optional<List<Character>> extract(final List<String> tokens) {
        final List<CharacterStat> characterStats = getCharactersStat(tokens);

        final List<Character> filteredCharactersStat = postFilter(filterCharacterStatisticFromNonEndCharacters(characterStats)
                .stream()
                .map(CharacterStat::getCharacter)
                .collect(Collectors.toList()), tokens);

        return Optional.of(filteredCharactersStat);
    }

    @VisibilityReducedForCLI
    List<CharacterStat> getCharactersStat(final List<String> tokens) {
        final List<String> filteredTokens = filter(tokens);

        final StatData endCharactersStatData = END_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);
        final StatData startCharactersStatData = START_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);

        return getCharactersStatistic(startCharactersStatData, endCharactersStatData);
    }

    @VisibilityReducedForTestPurposeOnly
    List<CharacterStat> filterCharacterStatisticFromNonEndCharacters(final List<CharacterStat> characterStats) {
        final SummaryStatistics stats = createSummaryStatistics();
        characterStats
                .stream()
                .mapToDouble(CharacterStat::getProbabilityThatEndCharacter)
                .forEach(item -> stats.addValue(item));

//        final double probabilityLevel = stats.getMean() - stats.getMean() * stats.getStandardDeviation();
//
//        return characterStats
//                .stream()
//                .filter(stat -> stat.getProbabilityThatEndCharacter() > probabilityLevel)
//                .collect(Collectors.toList());
        final List<CharacterStat> filteredCharacterStats = characterStats
                .stream()
                .sorted((i1, i2) -> i1.getProbabilityThatEndCharacter().compareTo(i2.getProbabilityThatEndCharacter()))
                .collect(Collectors.toList())
                .subList((int) ((double) characterStats.size() * .7), characterStats.size());
        return filteredCharacterStats;
    }

    @VisibilityReducedForTestPurposeOnly
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

    @VisibilityReducedForTestPurposeOnly
    List<String> filter(final List<String> tokens) {
        return tokens.parallelStream()
                .map(String::toLowerCase).map(TokenMappers::removeMultipleEndCharacters)
                .filter(token -> token.length() > MINIMUM_TOKEN_SIZE)
                .collect(Collectors.toList());
    }

    @VisibilityReducedForTestPurposeOnly
    SummaryStatistics createSummaryStatistics() {
        return new SummaryStatistics();
    }

    private List<Character> postFilter(final List<Character> separators, final List<String> tokens) {

        final List<String> filteredTokens = filter(tokens);

        final StatData endCharactersStatData = END_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);
        final StatData startCharactersStatData = START_CHARACTER_STAT_DATA_EXTRACTOR.parseStat(filteredTokens);

//        final List<Double> probs = separators.stream().map(splitor -> endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter2(splitor)).collect(Collectors.toList());
//        final List<Character> result = separators.stream().filter(splitor -> endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter2(splitor) > .06).collect(Collectors.toList());

        final List<Double> probs = separators.stream().map(splitor -> endCharactersStatData.getProbabilityThatCharacterOnEdge(splitor)).collect(Collectors.toList());
        final List<Character> result = separators
                .stream()
                .filter(splitor -> endCharactersStatData.getProbabilityThatCharacterOnEdge(splitor) > .65)
                .filter(ch -> endCharactersStatData.getCharacterCount(ch) > 10)
                .collect(Collectors.toList());

        return result;

//        final Map<Character, Map<Character, Integer>> connections = new HashMap<>();
//        tokens.forEach(token -> {
//            if (token.length() < 3) {
//                return;
//            }
//            final Character last = token.charAt(token.length() - 1);
//            final Character beforeLast = token.charAt(token.length() - 2);
//            final Character beforeBeforeLast = token.charAt(token.length() - 3);
//            if (last.equals(beforeLast) || beforeBeforeLast.equals(beforeLast)) {
//                return;
//            }
//            if (separators.contains(last) && separators.contains(beforeLast) && separators.contains(beforeBeforeLast)) {
//                if (!connections.keySet().contains(beforeBeforeLast)) {
//                    connections.put(beforeBeforeLast, new HashMap<>());
//                }
//                final Integer level = connections.get(beforeBeforeLast).getOrDefault(last, 0);
//                connections.get(beforeBeforeLast).put(last, level + 1);
//            }
//        });
//        final Map<Character, Integer> itemWeight = new HashMap<>();
//        connections.entrySet().forEach(element -> {
//            final Set<Character> keys = element.getValue().keySet() ;
//            keys.forEach(key -> {
//                final int weight = itemWeight.getOrDefault(key, 0);
//                itemWeight.put(key, weight + element.getValue().get(key));
//
//                final int mainWeight = itemWeight.getOrDefault(element.getKey(), 0);
//                itemWeight.put(element.getKey(), mainWeight - element.getValue().get(key));
//            });
//        });
//        return itemWeight.keySet().stream().filter(key -> itemWeight.get(key) >= 0).collect(Collectors.toList());
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
