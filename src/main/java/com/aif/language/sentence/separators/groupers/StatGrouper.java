package com.aif.language.sentence.separators.groupers;


import com.aif.language.common.VisibilityReducedForTestPurposeOnly;
import com.aif.language.token.TokenMappers;

import java.util.*;
import java.util.stream.Collectors;

class StatGrouper implements ISeparatorsGrouper {

    private static final double START_LIMIT = .4;

    private static final double QUALITY_STEP = 0.05;

    @Override
    public List<Set<Character>> group(final List<String> tokens, final List<Character> splitters) {

        final List<String> filteredTokens = filterTokens(tokens);

        final Map<Character, Map<Character, Integer>> connections = parsConnections(filteredTokens, splitters);

        filterConnections(connections);

        final List<CharactersGroup> groups = parsGroup(connections);

        return convert(groups);
    }

    @VisibilityReducedForTestPurposeOnly
    List<Set<Character>> convert(final List<CharactersGroup> groups) {
        return groups
                .stream()
                .map(CharactersGroup::getSplitters)
                .collect(Collectors.toList());
    }

    @VisibilityReducedForTestPurposeOnly
    List<CharactersGroup> parsGroup(final Map<Character, Map<Character, Integer>> connections) {
        double limit = START_LIMIT;
        double prevLimit = 1.;
        List<CharactersGroup> lastCorrectResult = null;
        do {
            final List<CharactersGroup> result = parsGroup(connections, limit);
            if (Math.abs(prevLimit - limit) < QUALITY_STEP) {
                if (lastCorrectResult != null) {
                    return lastCorrectResult;
                }
                return result;
            }
            if (result.size() == 2) {
                lastCorrectResult = result;
            }
            if (result.size() >= 2) {
                prevLimit = limit;
                limit /= 2.;
            } else if (result.size() < 2) {
                limit = (limit + prevLimit) / 2.;
            }
        } while (true);
    }

    @VisibilityReducedForTestPurposeOnly
    List<CharactersGroup> parsGroup(final Map<Character, Map<Character, Integer>> connections, final double limit) {
        final List<CharactersGroup> groups = new ArrayList<>();
        connections
                .keySet()
                .stream()
                .sorted((key1, key2) -> ((Integer)connections.get(key1).keySet().size())
                        .compareTo(connections.get(key2).keySet().size()))
                .forEach(
                        key -> {
                            if (connections.get(key).isEmpty()) return;
                            if (connections.get(key).keySet().size() <= 3) return;

                            final Map<Character, Double> characters = convertConnections(connections.get(key));

                            addCharactersToGroup(characters, key, groups, limit);
                        }
                );
        return groups;
    }

    @VisibilityReducedForTestPurposeOnly
    void addCharactersToGroup(final Map<Character, Double> characters, final Character root, final List<CharactersGroup> groups, final double limit) {
        if (groups.isEmpty()) {
            final CharactersGroup charactersGroup = new CharactersGroup(characters, root);
            groups.add(charactersGroup);
            return;
        }

        for (CharactersGroup charactersGroup : groups) {
            if (charactersGroup.closeTo(characters) > limit) {
                charactersGroup.addCharacters(characters);
                charactersGroup.addSplitter(root);
                return;
            }
        }

        final CharactersGroup charactersGroup = new CharactersGroup(characters, root);
        groups.add(charactersGroup);
        return;
    }

    @VisibilityReducedForTestPurposeOnly
    Map<Character, Double> convertConnections(final Map<Character, Integer> connections) {
        final Map<Character, Double> convertedConnections = new HashMap<>();
        connections.keySet().forEach(key1 -> {
            final double sum = (double)connections.keySet().stream().mapToInt(key2 -> connections.get(key2)).sum();
            convertedConnections.put(key1, (double)connections.get(key1) / sum);
        });
        return convertedConnections;
    }

    @VisibilityReducedForTestPurposeOnly
    void filterConnections(final Map<Character, Map<Character, Integer>> connections) {
        connections.keySet().forEach(key -> {
            final Map<Character, Integer> currentConnection = connections.get(key);
            if (currentConnection.isEmpty()) {
                return;
            }

            final Map<Character, Integer> filteredConnection = new HashMap<>();

            final List<Character> keysSorted = currentConnection.keySet()
                    .stream()
                    .sorted((key1, key2) -> currentConnection.get(key1).compareTo(currentConnection.get(key2)))
                    .collect(Collectors.toList());

            for (int i = (int)((double)keysSorted.size() * .8); i < keysSorted.size(); i++) {
                filteredConnection.put(keysSorted.get(i), currentConnection.get(keysSorted.get(i)));
            }

            connections.put(key, filteredConnection);
        });
    }

    @VisibilityReducedForTestPurposeOnly
    List<String> filterTokens(final List<String> tokens) {
        return tokens
                .parallelStream()
                .map(TokenMappers::removeMultipleEndCharacters)
                .map(token -> {
                    if (token.length() <= 3) {
                        return null;
                    }
                    return token;
                })
                .collect(Collectors.toList());
    }

    @VisibilityReducedForTestPurposeOnly
    Map<Character, Map<Character, Integer>> parsConnections(final List<String> tokens, final List<Character> splitters) {
        final Map<Character, Map<Character, Integer>> connections = new HashMap<>();

        splitters.forEach(splitter -> connections.put(splitter, new HashMap<>()));

        for (int i = 0; i < tokens.size() - 1; i++) {
            final String currentToken = tokens.get(i);
            final String nextToken = tokens.get(i + 1);

            if (currentToken == null || nextToken == null) {
                continue;
            }

            final Character lastCharacter = currentToken.charAt(currentToken.length() - 1);
            if (splitters.contains(lastCharacter)) {
                final Character nextCharacter = nextToken.charAt(0);
                if (splitters.contains(nextCharacter)) {
                    continue;
                }
                if (!connections.get(lastCharacter).containsKey(nextCharacter)) {
                    connections.get(lastCharacter).put(nextCharacter, 0);
                }
                final Integer count = connections.get(lastCharacter).get(nextCharacter);
                connections.get(lastCharacter).put(nextCharacter, count + 1);
            }
        }

        return connections;
    }

    @VisibilityReducedForTestPurposeOnly
    static class CharactersGroup {

        private final Map<Character, Double> groupCharacters;

        private final Set<Character> splitters = new HashSet<>();

        @VisibilityReducedForTestPurposeOnly
        CharactersGroup(final Map<Character, Double> characters, final Character spliter) {
            this.groupCharacters = characters;
            this.splitters.add(spliter);
        }

        public double closeTo(final Map<Character, Double> characters) {
            if (groupCharacters.size() == 0) {
                return 1.;
            }

            final double commonCharacters = characters.keySet().stream().mapToDouble(ch -> {
                if (groupCharacters.keySet().contains(ch)) return characters.get(ch) * groupCharacters.get(ch);
                return 0.;
            }).sum();
            return commonCharacters /
                    (double)Math.min(characters.size(), groupCharacters.size());
        }

        public Set<Character> getSplitters() {
            return splitters;
        }

        public void addCharacters(final Map<Character, Double> characters) {
            characters.entrySet().forEach(element -> {
                if (groupCharacters.keySet().contains(element.getKey())) {
                    final double value = groupCharacters.get(element.getKey());
                    groupCharacters.put(element.getKey(), (value + element.getValue()) / 2.);
                } else {
                    groupCharacters.put(element.getKey(), element.getValue());
                }
            });
        }

        public void addSplitter(final Character splitter) {
            this.splitters.add(splitter);
        }

    }

}
