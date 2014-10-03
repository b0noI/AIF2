package com.aif.language.sentence.splitters;

import com.aif.language.common.VisibilityReducedForTestPurposeOnly;
import com.aif.language.sentence.separators.clasificators.ISentenceSeparatorGroupsClassificatory;
import com.aif.language.sentence.separators.extractors.ISentenceSeparatorExtractor;
import com.aif.language.sentence.separators.groupers.ISentenceSeparatorsGrouper;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

class SimpleSentenceSplitter extends AbstractSentenceSplitter {

    public SimpleSentenceSplitter(final ISentenceSeparatorExtractor sentenceSeparatorExtractor,
                                  final ISentenceSeparatorsGrouper sentenceSeparatorsGrouper,
                                  final ISentenceSeparatorGroupsClassificatory sentenceSeparatorGroupsClassificatory) {
        super(sentenceSeparatorExtractor, sentenceSeparatorsGrouper, sentenceSeparatorGroupsClassificatory);
    }

    public SimpleSentenceSplitter() {
        this(ISentenceSeparatorExtractor.Type.PROBABILITY.getInstance(),
                ISentenceSeparatorsGrouper.Type.PROBABILITY.getInstance(),
                ISentenceSeparatorGroupsClassificatory.Type.PROBABILITY.getInstance());
    }

    @Override
    public List<List<String>> split(final List<String> tokens, final Map<ISentenceSeparatorGroupsClassificatory.Group, Set<Character>> splitters) {

        final List<Boolean> listOfPositions = SimpleSentenceSplitter.mapToBooleans(tokens, splitters.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1));

        final SentenceIterator sentenceIterator = new SentenceIterator(tokens, listOfPositions);

        final List<List<String>> sentences = new ArrayList<>();
        while (sentenceIterator.hasNext()) {
            sentences.add(sentenceIterator.next());
        }

        super.getLogger().debug(String.format("Founded %d sentences", sentences.size()));

        return sentences;
    }

    @VisibilityReducedForTestPurposeOnly
    static List<Boolean> mapToBooleans(final List<String> tokens, final Set<Character> separators) {
        final List<Boolean> result = new ArrayList<>(tokens.size());

        for (int i = 0; i < tokens.size(); i++) {
            final String token = tokens.get(i);
            if (separators.contains(token.charAt(token.length() - 1))) {
                result.add(true);
            } else if (i != tokens.size() - 1 && separators.contains(token.charAt(0))) {
                result.add(true);
            } else {
                result.add(false);
            }
        }

        return result;
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
