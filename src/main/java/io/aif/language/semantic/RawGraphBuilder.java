package io.aif.language.semantic;

import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassifier;
import io.aif.language.word.IWord;
import io.aif.language.common.StringHelper;

import java.util.*;
import java.util.stream.Collectors;


class RawGraphBuilder {

    private static final int DEFAULT_CONNECT_AHEAD = 5;

    private final int connectAhead;

    private final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separators;

    public RawGraphBuilder(final int connectAhead,
                           final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separators) {
        this.connectAhead = (connectAhead > 0 ) ? connectAhead : DEFAULT_CONNECT_AHEAD;
        this.separators = separators;
    }

    public RawGraphBuilder(final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separators) {
        this(DEFAULT_CONNECT_AHEAD, separators);
    }

    public Map<IWord, Map<IWord, List<Double>>> build(final Collection<IWord.IWordPlaceholder> placeholders) {
        final List<IWord> words = placeholders
                .parallelStream()
                .map(IWord.IWordPlaceholder::getWord)
                .collect(Collectors.toList());

        final int connectAheadNormalized = (connectAhead > words.size())
                ? words.size()
                : connectAhead;

        //TODO Parellelize
        Map<IWord, Map<IWord, List<Double>>> graph = new HashMap<>();
        IWord key;
        for (int i = 0; i < words.size(); i++) {
            int multiplier = 1;
            key = words.get(i);
            graph.putIfAbsent(key, new HashMap<>());

            for (int j = 1; j <= connectAheadNormalized && i + j < words.size(); j++) {
                final IWord valueKey = words.get(i + j);

                Character charToken = StringHelper
                        .castToChar(valueKey.getRootToken())
                        .orElse(null);
                if (charToken != null)
                    multiplier += getMultiplierValue(charToken);

                graph.get(key).putIfAbsent(valueKey, new ArrayList<>());
                graph.get(key).get(valueKey).add((double)(j * multiplier));
            }
        }

        return graph;
    }

    private int getMultiplierValue(Character charToken) {
        int result = 0;
        // TODO: Can we have the same seperator in both the groups.
        // TODO: contains does not have for string
        if (separators.get(ISeparatorGroupsClassifier.Group.GROUP_2).contains(charToken)) {
            result = 1;
        }
        if (separators.get(ISeparatorGroupsClassifier.Group.GROUP_1).contains(charToken)) {
            result = 4;
        }
        return result;
    }
}
