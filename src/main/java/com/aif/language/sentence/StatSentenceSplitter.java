package com.aif.language.sentence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

class StatSentenceSplitter implements ISentenceSeparatorExtractor {

    @Override
    public List<Character> getSeparators(List<String> tokens) {
        return null;
    }

    private static StatData parseStat(final List<String> sentence) {
        final StatData statData = new StatData();
        sentence.parallelStream().filter(token -> token.length() > 2).forEach(token -> {
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

        public double getProbabilityThatCharacterIsSplitterCharacter(final Character ch) {
            return getProbabiltyThatCharacterInTheEnd(ch) * getProbablityThatCharacterBeforeIsEndCharacter(ch);
        }

        private Map<Character, Integer> getMapForEndCharacter(final Character endCharacter) {
            if (!charactersBeforeEndCharacter.containsKey(endCharacter)) {
                synchronized (charactersBeforeEndCharacter) {
                    return charactersBeforeEndCharacter.getOrDefault(endCharacter, new ConcurrentHashMap<Character, Integer>());
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

}
