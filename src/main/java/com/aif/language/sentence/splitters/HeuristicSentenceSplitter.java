package com.aif.language.sentence.splitters;


import com.aif.language.sentence.separators.clasificators.ISentenceSeparatorGroupsClassificatory;
import com.aif.language.sentence.separators.extractors.ISentenceSeparatorExtractor;
import com.aif.language.sentence.separators.groupers.ISentenceSeparatorsGrouper;

import java.util.*;

class HeuristicSentenceSplitter extends AbstractSentenceSplitter {

    public HeuristicSentenceSplitter(final ISentenceSeparatorExtractor sentenceSeparatorExtractor,
                              final ISentenceSeparatorsGrouper sentenceSeparatorsGrouper,
                              final ISentenceSeparatorGroupsClassificatory sentenceSeparatorGroupsClassificatory) {
        super(sentenceSeparatorExtractor, sentenceSeparatorsGrouper, sentenceSeparatorGroupsClassificatory);
    }

    public HeuristicSentenceSplitter() {
        this(ISentenceSeparatorExtractor.Type.PROBABILITY.getInstance(),
                ISentenceSeparatorsGrouper.Type.PROBABILITY.getInstance(),
                ISentenceSeparatorGroupsClassificatory.Type.PROBABILITY.getInstance());
    }

    @Override
    public List<Boolean> split(final List<String> tokens, final Map<ISentenceSeparatorGroupsClassificatory.Group, Set<Character>> splitters) {
        final Map<ISentenceSeparatorGroupsClassificatory.Group, Map<Character, Double>> connections = new HashMap<>();
        connections.put(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1, new HashMap<>());
        connections.put(ISentenceSeparatorGroupsClassificatory.Group.GROUP_2, new HashMap<>());

        for (int i = 0; i < tokens.size() - 1; i++) {
            final String currentToken = tokens.get(i);
            final String nextToken = tokens.get(i + 1);
            final Character nextFirstCharacter = nextToken.charAt(0);
            if (splitters.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1).contains(currentToken.charAt(currentToken.length() - 1))) {
                connections.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1).merge(nextFirstCharacter, 1., (v1, v2) -> v1 + v2);
            } else {
                connections.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_2).merge(nextFirstCharacter, 1., (v1, v2) -> v1 + v2);
            }
        }

        for (ISentenceSeparatorGroupsClassificatory.Group group : ISentenceSeparatorGroupsClassificatory.Group.values()) {
            final Map<Character, Double> groupConnections = connections.get(group);
            double max = groupConnections.entrySet().stream().mapToDouble(Map.Entry::getValue).max().getAsDouble();
            groupConnections.keySet().forEach(key -> groupConnections.put(key, groupConnections.get(key) / max));
        }

        final List<Boolean> booleans = new ArrayList<>();

        for (int i = 0; i < tokens.size() - 1; i++) {
            final String currentToken = tokens.get(i);
            if (splitters.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1).contains(currentToken.charAt(currentToken.length() - 1))) {
                final String nextToken = tokens.get(i + 1);
                final Character nextFirstCharacter = nextToken.charAt(0);
                if (!connections.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_2).keySet().contains(nextFirstCharacter)) {

                    booleans.add(true);
                } else {
                    final double isSep = connections.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1).get(nextFirstCharacter);
                    final double isNotSep = connections.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_2).get(nextFirstCharacter);
                    if (isSep >= isNotSep) {
                        booleans.add(true);
                    } else {
                        booleans.add(false);
                    }
                }
            } else {
                booleans.add(false);
            }

        }
        booleans.add(false);
        return booleans;
    }

}