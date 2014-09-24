package com.aif.language.sentence.separators.groupers;


import com.aif.language.token.TokenMappers;

import java.util.*;
import java.util.stream.Collectors;

class StatGrouper implements ISentenceSeparatorsGrouper {

    @Override
    public Map<Group, Set<Character>> group(final List<String> tokens, final List<Character> splitters) {

        final List<String> filteredTokens = filterTokens(tokens);

        final Map<Character, Map<Character, Integer>> connections = parsConnections(filteredTokens, splitters);

        filterConnections(connections);

        // all test shows that this magic .4 works much batter then any smart adaptive methods
        final List<CharactersGroup> groups = parsGroup(connections, .4);

        return group(groups);
    }

    // TODO: this method should be done in different way!
    private Map<Group, Set<Character>> group(final List<CharactersGroup> groups) {
        final Set<Character> group1 = new HashSet<>();
        final Set<Character> group2 = new HashSet<>();

        groups.forEach(gr -> {
            if (gr.getSplitters().contains('.')) {
                group1.addAll(gr.getSplitters());
            } else {
                group2.addAll(gr.getSplitters());
            }
        });
        final Map<Group, Set<Character>> result = new HashMap<>();
        result.put(Group.GROUP_1, group1);
        result.put(Group.GROUP_2, group2);
        return result;
    }

//    private List<CharactersGroup> parsGroup(final Map<Character, Map<Character, Integer>> connections) {
//        double limit = .4;
//        double prevLimit = 1.;
//        do {
//            List<CharactersGroup> result = parsGroup(connections, limit);
//            if (result.size() > 2) {
//                prevLimit = limit;
//                limit /= 2.;
//            } else if (result.size() < 2) {
//                limit = (limit + prevLimit) / 2.;
//            } else {
//                return result;
//            }
//        } while (true);
//    }

    private List<CharactersGroup> parsGroup(final Map<Character, Map<Character, Integer>> connections, final double limit) {
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

                            final Set<Character> characters = new HashSet<>(connections.get(key).keySet());

                            if (groups.isEmpty()) {
                                final CharactersGroup charactersGroup = new CharactersGroup(characters, key);
                                groups.add(charactersGroup);
                                return;
                            }

                            for (CharactersGroup charactersGroup : groups) {
                                if (charactersGroup.closeTo(characters) > limit) {
                                    charactersGroup.addCharacters(characters);
                                    charactersGroup.addSplitter(key);
                                    return;
                                }
                            }
                            final CharactersGroup charactersGroup = new CharactersGroup(characters, key);
                            groups.add(charactersGroup);
                        }
                );
        return groups;
    }

    private void filterConnections(final Map<Character, Map<Character, Integer>> connections) {
        connections.keySet().forEach(key -> {
            final Map<Character, Integer> currentConnection = connections.get(key);
            if (currentConnection.isEmpty()) {
                return;
            }
            final int max = currentConnection
                    .entrySet()
                    .stream()
                    .mapToInt(element -> element.getValue())
                    .max()
                    .getAsInt();

            final int minLevel = (int)((double)max * .2);

            final Map<Character, Integer> filteredConnection = new HashMap<>();
            currentConnection.entrySet().forEach(element -> {
                if (element.getValue() > minLevel) {
                    filteredConnection.put(element.getKey(), element.getValue());
                }
            });

            connections.put(key, filteredConnection);
        });
    }

    private List<String> filterTokens(final List<String> tokens) {
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

    private Map<Character, Map<Character, Integer>> parsConnections(final List<String> tokens, final List<Character> splitters) {
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
                    connections.get(lastCharacter).put(nextCharacter, 1);
                }
                final Integer count = connections.get(lastCharacter).get(nextCharacter);
                connections.get(lastCharacter).put(nextCharacter, count + 1);
            }
        }

        return connections;
    }

    private static class CharactersGroup {

        private final Set<Character> groupCharacters;

        private final Set<Character> splitters = new HashSet<>();

        private CharactersGroup(Set<Character> characters, final Character spliter) {
            this.groupCharacters = characters;
            this.splitters.add(spliter);
        }

        public double closeTo(final Collection<Character> characters) {
            final int commonCharacters = characters.stream().mapToInt(ch -> {
                if (groupCharacters.contains(ch)) return 1;
                return 0;
            }).sum();
            return (double)commonCharacters /
                    (double)Math.min(characters.size(), groupCharacters.size());
        }

        public Set<Character> getGroupCharacters() {
            return groupCharacters;
        }

        public Set<Character> getSplitters() {
            return splitters;
        }

        public void addCharacters(final Collection<Character> characters) {
            groupCharacters.addAll(characters);
        }

        public void addSplitter(final Character splitter) {
            this.splitters.add(splitter);
        }

    }

}
