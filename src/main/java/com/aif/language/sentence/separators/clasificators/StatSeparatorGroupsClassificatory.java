package com.aif.language.sentence.separators.clasificators;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class StatSeparatorGroupsClassificatory implements ISeparatorGroupsClassificatory {

    @Override
    public Map<Group, Set<Character>> classify(final List<String> tokens, final List<Set<Character>> separatorsGroups) {
        final Map<Group, Set<Character>> result = new HashMap<>();

        if (separatorsGroups.size() <= 1) {
            if (separatorsGroups.size() == 0) {
                result.put(Group.GROUP_1, new HashSet<>());
            } else {
                result.put(Group.GROUP_1, separatorsGroups.get(0));
            }
            result.put(Group.GROUP_2, new HashSet<>());
            return result;
        }

        final List<Integer> mapTokensToGroups = mapTokensToGroups(tokens, separatorsGroups);

        final Map<Integer, Map<Character, Integer>> connections = new HashMap<>();
        for (int i = 0; i < separatorsGroups.size() + 1; i++) {
            connections.put(i, new HashMap<>());
        }

        for (int i = 1; i < tokens.size(); i++) {
            final int group = mapTokensToGroups.get(i - 1);
            final Character ch = tokens.get(i).charAt(0);
            if (!connections.get(group).keySet().contains(ch)) {
                connections.get(group).put(ch, 0);
            }
            final int count = connections.get(group).get(ch);
            connections.get(group).put(ch, count + 1);
        }

        final Function<Map<Character, Integer>, Map<Character, Double>> mapper = map -> {
            final Map<Character, Double> mappingResult = new HashMap<>();
            final OptionalInt max = map.entrySet().stream().mapToInt(Map.Entry::getValue).max();
            if (!max.isPresent()) {
                return mappingResult;
            }
            map.keySet().forEach(key -> mappingResult.put(key, (double)map.get(key) / (double)max.getAsInt()));
            return mappingResult;
        };

        final Function<Map<Character, Double>, Map<Character, Double>> filter = map -> {
            final Map<Character, Double> filteredResults = new HashMap<>();

            final List<Map.Entry<Character, Double>> list = map.entrySet().stream().sorted((e1, e2) -> {
                return e1.getValue().compareTo(e2.getValue());
            }).collect(Collectors.toList());

            for (int i = (int)((double)list.size() * .7); i < list.size(); i++) {
                filteredResults.put(list.get(i).getKey(), list.get(i).getValue());
            }

            return filteredResults;
        };

        final Map<Integer, Map<Character, Double>> convertedGroupsMap = new HashMap<>();

        connections.keySet().stream().forEach(key ->
            convertedGroupsMap.put(key, mapper.andThen(filter).apply(connections.get(key))));

        final List<Double> p = new ArrayList<>();
        for (int i = 0; i < separatorsGroups.size(); i++) {
            p.add(distance(convertedGroupsMap.get(i + 1), convertedGroupsMap.get(0)));
        }

        double max = p.stream().mapToDouble(i -> i).max().getAsDouble();

        result.put(Group.GROUP_1, new HashSet<>());
        result.put(Group.GROUP_2, new HashSet<>());

        for (int i = 0; i < p.size(); i++) {
            if (p.get(i) == max) {
                result.get(Group.GROUP_2).addAll(separatorsGroups.get(i));
            } else {
                result.get(Group.GROUP_1).addAll(separatorsGroups.get(i));
            }
        }

        return result;
    }

    private double distance(final Map<Character, Double> from, final Map<Character, Double> to) {
        final OptionalDouble averager = from.keySet().stream().mapToDouble(key -> {
            if (!to.keySet().contains(key))
                return .0;

            return (from.get(key) + to.get(key)) / 2.;
        }).average();
        if (!averager.isPresent()) {
            return .0;
        }
        return averager.getAsDouble();
    }

    private List<Integer> mapTokensToGroups(final List<String> tokens, final List<Set<Character>> separatorsGroups) {
        return tokens
                .stream()
                .map(
                        token -> {
                            if (token.isEmpty()) return 0;
                            for (Integer i = 0; i < separatorsGroups.size(); i++) {
                                final Set<Character> separatorGroup = separatorsGroups.get(i);
                                if (separatorGroup.contains(token.charAt(0))) {
                                    return i + 1;
                                }
                                if (separatorGroup.contains(token.charAt(token.length() - 1))) {
                                    return i + 1;
                                }
                            }
                            return 0;
                        }
                ).collect(Collectors.toList());
    }

}
