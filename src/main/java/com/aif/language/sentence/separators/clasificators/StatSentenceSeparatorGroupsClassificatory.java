package com.aif.language.sentence.separators.clasificators;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class StatSentenceSeparatorGroupsClassificatory implements ISentenceSeparatorGroupsClassificatory {

    @Override
    public Map<Group, Set<Character>> classify(final List<String> tokens, final List<Set<Character>> separatorsGroups) {
        final Map<Group, Set<Character>> result = new HashMap<>();

        if (separatorsGroups.size() == 1) {
            result.put(Group.GROUP_1, separatorsGroups.get(0));
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

//        if (p2 > p1) {
//            result.put(Group.GROUP_2, separatorsGroups.get(1));
//            result.put(Group.GROUP_1, separatorsGroups.get(0));
//        } else {
//            result.put(Group.GROUP_2, separatorsGroups.get(0));
//            result.put(Group.GROUP_1, separatorsGroups.get(1));
//        }
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

//    @Override
//    public Map<Group, Set<Character>> classify(final List<String> tokens, final List<Set<Character>> separatorsGroups) {
//        final List<Integer> mapTokensToGroups = mapTokensToGroups(tokens, separatorsGroups);
//
//        final Map<Integer, List<Integer>> groupsIntegrationFactor = new HashMap<>();
//        final Map<Integer, Integer> tempGroupIntegrationFactorCalculator = new HashMap<>();
//        final Map<Integer, Integer> lastPosition = new HashMap<>();
//        final Map<Integer, List<Integer>> distances = new HashMap<>();
//
//        for (int i = 1; i <= separatorsGroups.size(); i++) {
//            groupsIntegrationFactor.put(i, new ArrayList<>());
//            tempGroupIntegrationFactorCalculator.put(i, 0);
//            lastPosition.put(i, 0);
//            distances.put(i, new ArrayList<>());
//        }
//
//        for (int i = 0; i < mapTokensToGroups.size(); i++) {
//            final Integer element = mapTokensToGroups.get(i);
//            if (element != 0) {
//
//                distances.get(element).add(i - lastPosition.get(element));
//                lastPosition.put(element, i);
//            }
//        }
//
//        distances.keySet().forEach(key -> {
//            final List<Integer> dist = distances.get(key);
//            final List<Integer> filteredDist = new ArrayList<>();
//            Collections.sort(dist);
//            final int limit = (int)((double)dist.size() * .8);
//            int i = 0;
//            while (filteredDist.size() < limit) {
//                filteredDist.add(dist.get(i++));
//            }
//            distances.put(key, filteredDist);
//        });
//
//        final Map<Integer, Double> sum = new HashMap<>();
////        groupsIntegrationFactor.keySet().stream().forEach(
////                key -> sum.put(key, groupsIntegrationFactor.get(key).stream().filter(i -> i != 0).mapToDouble(i -> (double)i).average().getAsDouble())
////        );
//        distances.keySet().forEach(
//                key -> {
//                    final SummaryStatistics stats = new SummaryStatistics();
//                    distances.get(key).forEach(element -> stats.addValue(element));
//                    sum.put(key, stats.getMean());
//                }
//        );
//
//        final Map<Group, Set<Character>> result = new HashMap<>();
//        result.put(Group.GROUP_2, new HashSet<>());
//        final double max = sum.entrySet().stream().mapToDouble(Map.Entry::getValue).max().getAsDouble();
//        sum.keySet().forEach(key -> {
//            if (sum.get(key) ==  max) {
//                result.put(Group.GROUP_1, separatorsGroups.get(key - 1));
//            } else {
//                result.get(Group.GROUP_2).addAll(separatorsGroups.get(key - 1));
//            }
//        });
//        return result;
//    }

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
