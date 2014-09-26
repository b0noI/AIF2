package com.aif.language.sentence.separators.clasificators;

import java.util.*;
import java.util.stream.Collectors;

class StatSentenceSeparatorGroupsClassificatory implements ISentenceSeparatorGroupsClassificatory {

    @Override
    public Map<Group, Set<Character>> classify(final List<String> tokens, final List<Set<Character>> separatorsGroups) {
        final List<Integer> mapTokensToGroups = tokens
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
        final Map<Integer, List<Integer>> groupsIntegrationFactor = new HashMap<>();
        final Map<Integer, Integer> tempGroupIntegrationFactorCalculator = new HashMap<>();
        final Map<Integer, Integer> lastPosition = new HashMap<>();
        for (int i = 1; i <= separatorsGroups.size(); i++) {
            groupsIntegrationFactor.put(i, new ArrayList<>());
            tempGroupIntegrationFactorCalculator.put(i, 0);
            lastPosition.put(i, 0);
        }

        for (int i = 0; i < mapTokensToGroups.size(); i++) {
            Integer element = mapTokensToGroups.get(i);
            if (element != 0) {
                groupsIntegrationFactor.get(element).add(tempGroupIntegrationFactorCalculator.get(element));
                tempGroupIntegrationFactorCalculator.put(element, 0);
                lastPosition.put(element, i);
                tempGroupIntegrationFactorCalculator
                        .keySet()
                        .stream()
                        .filter(key -> key != element)
                        .forEach(key ->
                                tempGroupIntegrationFactorCalculator.put(key, tempGroupIntegrationFactorCalculator.get(key) + 1));
            } else {
                for (Integer key : lastPosition.keySet()) {
                    if (i - lastPosition.get(key) > 20) {
                        lastPosition.put(key, i);
                        tempGroupIntegrationFactorCalculator.put(key, 0);
                    }
                }
            }
        }

        Map<Integer, Double> sum = new HashMap<>();
        groupsIntegrationFactor.keySet().forEach(key -> sum.put(key, groupsIntegrationFactor.get(key).stream().mapToDouble(i -> (double)i).average().getAsDouble()));

        return null;

    }

}
