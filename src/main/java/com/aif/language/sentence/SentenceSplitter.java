package com.aif.language.sentence;

import com.aif.language.common.ISplitter;
import com.aif.language.common.RegexpCooker;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by andriikr on 14/06/2014.
 */
public class SentenceSplitter implements ISplitter<List<String>, List<String>> {

    private         final   ISentenceSeparatorExtractor sentenceSeparatorExtractor;

    public SentenceSplitter(final ISentenceSeparatorExtractor sentenceSeparatorExtractor) {
        this.sentenceSeparatorExtractor = sentenceSeparatorExtractor;
    }

    public SentenceSplitter() {
        this(ISentenceSeparatorExtractor.Type.PREDEFINED.getInstance());
    }

    @Override
    public List<List<String>> split(final List<String> tokens) {
        final List<Boolean> listOfPositions = mapToBooleans(tokens);

        final SentenceIterator sentenceIterator = new SentenceIterator(tokens, listOfPositions);

        final List<List<String>> sentances = new ArrayList<>();
        while (sentenceIterator.hasNext()) {
            sentances.add(sentenceIterator.next());
        }
        return sentances;
    }

    private List<Boolean> mapToBooleans(final List<String> tokens) {
        final Optional<List<Character>> optionalSeparators = sentenceSeparatorExtractor.extract(tokens);

        if (!optionalSeparators.isPresent()) {
            return Arrays.asList(new Boolean[tokens.size()]);
        }

        final List<Character> separators = optionalSeparators.get();

        return tokens.stream()
                .map(token -> separators.contains(token.charAt(token.length() - 1)))
                .collect(Collectors.toList());
    }

    private static class SentenceIterator implements Iterator<List<String>> {

        private final   List<String>    tokens;

        private final   List<Boolean>   endTokens;

        private         int             currentPosition = 0;

        private SentenceIterator(List<String> tokens, List<Boolean> endTokens) {
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
            while(startIndex < endTokens.size() - 1) {
                startIndex++;
                if (endTokens.get(startIndex)) {
                    startIndex++;
                    return startIndex;
                }
            }
            return startIndex + 1;
        }

    }

}
