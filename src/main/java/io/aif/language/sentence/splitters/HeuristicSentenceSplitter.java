package io.aif.language.sentence.splitters;


import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassifier;
import io.aif.language.sentence.separators.extractors.ISeparatorExtractor;
import io.aif.language.sentence.separators.groupers.ISeparatorsGrouper;

import java.util.*;

class HeuristicSentenceSplitter extends AbstractSentenceSplitter {

    public HeuristicSentenceSplitter(final ISeparatorExtractor sentenceSeparatorExtractor,
                              final ISeparatorsGrouper sentenceSeparatorsGrouper,
                              final ISeparatorGroupsClassifier sentenceSeparatorGroupsClassificatory) {
        super(sentenceSeparatorExtractor, sentenceSeparatorsGrouper, sentenceSeparatorGroupsClassificatory);
    }

    public HeuristicSentenceSplitter() {
        this(ISeparatorExtractor.Type.getDefault(),
                ISeparatorsGrouper.Type.PROBABILITY.getInstance(),
                ISeparatorGroupsClassifier.Type.PROBABILITY.getInstance());
    }

    @Override
    public List<Boolean> split(final List<String> tokens, final Map<ISeparatorGroupsClassifier.Group, Set<Character>> splitters) {
        final Map<ISeparatorGroupsClassifier.Group, Map<Character, Double>> connections = new HashMap<>();
        connections.put(ISeparatorGroupsClassifier.Group.GROUP_1, new HashMap<>());
        connections.put(ISeparatorGroupsClassifier.Group.GROUP_2, new HashMap<>());

        for (int i = 0; i < tokens.size() - 1; i++) {
            final String currentToken = tokens.get(i);
            final String nextToken = tokens.get(i + 1);
            final Character nextFirstCharacter = nextToken.charAt(0);
            if (splitters.get(ISeparatorGroupsClassifier.Group.GROUP_1).contains(currentToken.charAt(currentToken.length() - 1))) {
                connections.get(ISeparatorGroupsClassifier.Group.GROUP_1).merge(nextFirstCharacter, 1., (v1, v2) -> v1 + v2);
            } else {
                connections.get(ISeparatorGroupsClassifier.Group.GROUP_2).merge(nextFirstCharacter, 1., (v1, v2) -> v1 + v2);
            }
        }

        for (ISeparatorGroupsClassifier.Group group : ISeparatorGroupsClassifier.Group.values()) {
            final Map<Character, Double> groupConnections = connections.get(group);
            final double sum = groupConnections.entrySet().stream().mapToDouble(Map.Entry::getValue).sum();
            groupConnections.keySet().forEach(key -> groupConnections.put(key, groupConnections.get(key) / sum));
        }

        final List<Boolean> booleans = new ArrayList<>();

        for (int i = 0; i < tokens.size() - 1; i++) {
            final String currentToken = tokens.get(i);
            if (splitters.get(ISeparatorGroupsClassifier.Group.GROUP_1).contains(currentToken.charAt(currentToken.length() - 1))) {
                final String nextToken = tokens.get(i + 1);
                final Character nextFirstCharacter = nextToken.charAt(0);
                if (!connections.get(ISeparatorGroupsClassifier.Group.GROUP_2).keySet().contains(nextFirstCharacter)) {

                    booleans.add(true);
                } else {
                    final double isSep = connections.get(ISeparatorGroupsClassifier.Group.GROUP_1).get(nextFirstCharacter);
                    final double isNotSep = connections.get(ISeparatorGroupsClassifier.Group.GROUP_2).get(nextFirstCharacter);
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
