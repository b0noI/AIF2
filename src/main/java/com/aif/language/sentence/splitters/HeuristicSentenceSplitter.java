package com.aif.language.sentence.splitters;


import com.aif.language.common.VisibilityReducedForTestPurposeOnly;
import com.aif.language.sentence.separators.clasificators.ISentenceSeparatorGroupsClassificatory;
import com.aif.language.sentence.separators.extractors.ISentenceSeparatorExtractor;
import com.aif.language.sentence.separators.groupers.ISentenceSeparatorsGrouper;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

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
    public List<List<String>> split(final List<String> tokens, final Map<ISentenceSeparatorGroupsClassificatory.Group, Set<Character>> splitters) {
        final Set<Character> sentenceSplitters = splitters.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1);
        final List<Integer> sentencesSize = new ArrayList<>();
        final Map<Character, Double> connections = new HashMap<>();
        int lastPosition = 0;
        for (int i = 0; i < tokens.size() - 1; i++) {
            final String token = tokens.get(i);
            if (sentenceSplitters.contains(token.charAt(0)) || sentenceSplitters.contains(token.charAt(token.length() - 1))) {
                sentencesSize.add(i - lastPosition);
                lastPosition = i;
                final Character nextChar = tokens.get(i + 1).charAt(0);
                if (connections.containsKey(nextChar)) {
                    connections.put(nextChar, connections.get(nextChar) + 1.);
                } else {
                    connections.put(nextChar, 1.);
                }
            }
        }

        final double max = connections.entrySet().stream().mapToDouble(Map.Entry::getValue).max().getAsDouble();
        connections.keySet().forEach(k -> connections.put(k, connections.get(k) / max));

        final SummaryStatistics stats = new SummaryStatistics();
        sentencesSize.forEach(size -> stats.addValue(size));
        final List<Boolean> booleans = new ArrayList<>(tokens.size());
        lastPosition = 0;
        for (int i = 0; i < tokens.size() - 1; i++) {
            final String token = tokens.get(i);
            if (sentenceSplitters.contains(token.charAt(0)) || sentenceSplitters.contains(token.charAt(token.length() - 1))) {
                int size = i - lastPosition;
                double level = stats.getMean() + stats.getStandardDeviation();
//                if (size > level) {
//                    booleans.add(true);
//                    continue;
//                }
                final Character nextChar = tokens.get(i + 1).charAt(0);
                final double prob = /*((double)size / level) */ (connections.get(nextChar));
                if (prob > 0.1) {
                    booleans.add(true);
                } else {
                    booleans.add(false);
                }
                lastPosition = i;

            } else {
                booleans.add(false);
            }
        }
        booleans.add(true);

        final SentenceIterator sentenceIterator = new SentenceIterator(tokens, booleans);

        final List<List<String>> sentences = new ArrayList<>();
        while (sentenceIterator.hasNext()) {
            sentences.add(sentenceIterator.next());
        }

        super.getLogger().debug(String.format("Founded %d sentences", sentences.size()));

        return sentences;
    }

    @VisibilityReducedForTestPurposeOnly
    static class SentenceIterator implements Iterator<List<String>> {

        private final   List<String>    tokens;

        private final   List<Boolean>   endTokens;

        private         int             currentPosition = 0;

        public SentenceIterator(List<String> tokens, List<Boolean> endTokens) {
            assert tokens != null;
            assert endTokens != null;
            assert tokens.size() == endTokens.size();
            this.tokens = tokens;
            this.endTokens = endTokens;
        }

        @Override
        public boolean hasNext() {
            return currentPosition != tokens.size();
        }

        @Override
        public List<String> next() {
            final List<String> sentence = getNextSentence();

            return sentence;
        }

        private List<String> getNextSentence() {
            final int oldIndex = currentPosition;
            currentPosition = getNextTrueIndex();
            return this.tokens.subList(oldIndex, currentPosition);
        }

        private int getNextTrueIndex() {
            int startIndex = currentPosition;

            if (endTokens.size() == startIndex) {
                return startIndex;
            }

            if (endTokens.size() == startIndex + 1) {
                return startIndex + 1;
            }

            do {
                if (endTokens.get(startIndex)) {
                    startIndex++;
                    return startIndex;
                }
                startIndex++;
            } while(startIndex < endTokens.size() - 1);
            return startIndex + 1;
        }

    }

}
