package com.aif.language.sentence;

import com.aif.language.common.IExtractor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class StatData {

    private final Map<Character, Map<Character, Integer>>   charactersNearEdgeCharacter = new ConcurrentHashMap<>();

    private final Map<Character, Integer>                   edgeCharacters = new ConcurrentHashMap<>();

    private final Map<Character, Integer>                   characters                    = new ConcurrentHashMap<>();

    private final IExtractor<String, Character>             edgeCharacterExtractor;

    private final IExtractor<String, Character>             characterNearEdgeCharacterExtractor;

    StatData(IExtractor<String, Character> edgeCharacterExtractor, IExtractor<String, Character> characterNearEdgeCharacterExtractor) {
        this.edgeCharacterExtractor = edgeCharacterExtractor;
        this.characterNearEdgeCharacterExtractor = characterNearEdgeCharacterExtractor;
    }

    public void addEdgeCharacter(final Character characterNearEdgeCharacter, final Character edgeCharacter) {
        final Character lowCaseCharacterBeforeEndCharacter = StatData.prepareCharacter(characterNearEdgeCharacter);
        final Character lowCaseEndCharacter = StatData.prepareCharacter(edgeCharacter);

        edgeCharacters.merge(lowCaseEndCharacter, 1, (v1, v2) -> v1 + v2);

        final Map<Character, Integer> characterBeforeEndCharacterMap = getMapForEdgeCharacter(edgeCharacter);
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
        return getProbabilityThatCharacterOnEdge(ch) * getProbabilityThatCharacterBeforeIsEdgeCharacter(ch);
    }

    public StatData parseStat(List<String> tokens) {
        final StatData endCharacterStatdata = new StatData(edgeCharacterExtractor, characterNearEdgeCharacterExtractor);
        tokens.parallelStream().filter(token -> token.length() > 2).forEach(token -> parsToken(token, endCharacterStatdata));
        return endCharacterStatdata;
    }

    private void parsToken(final String token, final StatData statData) {
        token.chars().forEach(ch -> statData.addCharacter((char) ch));

        final Optional<Character> edgeCharacter = edgeCharacterExtractor.extract(token);
        final Optional<Character> characterNearEdge = characterNearEdgeCharacterExtractor.extract(token);

        if (!edgeCharacter.isPresent() || !characterNearEdge.isPresent()) {
            return;
        }

        statData.addEdgeCharacter(characterNearEdge.get(), edgeCharacter.get());
    }

    private Map<Character, Integer> getMapForEdgeCharacter(final Character endCharacter) {
        if (!charactersNearEdgeCharacter.containsKey(endCharacter)) {
            synchronized (charactersNearEdgeCharacter) {
                final Map<Character, Integer> targetMap = charactersNearEdgeCharacter.getOrDefault(endCharacter, new ConcurrentHashMap<Character, Integer>());
                charactersNearEdgeCharacter.put(endCharacter, targetMap);
                return targetMap;
            }
        }
        return charactersNearEdgeCharacter.getOrDefault(endCharacter, new ConcurrentHashMap<>());
    }

    private double getProbabilityThatCharacterOnEdge(final Character ch) {
        final Character lowCaseCharacter = StatData.prepareCharacter(ch);
        return (double) edgeCharacters.getOrDefault(lowCaseCharacter, 0) /
                (double) characters.getOrDefault(lowCaseCharacter, 0);
    }

    private double getProbabilityThatCharacterBeforeIsEdgeCharacter(final Character ch) {
        final Character lowCaseCharacter = StatData.prepareCharacter(ch);
        final Map<Character, Integer> beforeCharacters = charactersNearEdgeCharacter.getOrDefault(lowCaseCharacter, new HashMap<>());

        return beforeCharacters.keySet()
                .stream()
                .mapToDouble(k -> getProbabilityThatCharacterOnEdge(k) * (double)beforeCharacters.get(k))
                .sum() / (double) edgeCharacters.getOrDefault(lowCaseCharacter, 1);
    }

    private static Character prepareCharacter(final Character ch) {
        return Character.toLowerCase(ch);
    }

}
