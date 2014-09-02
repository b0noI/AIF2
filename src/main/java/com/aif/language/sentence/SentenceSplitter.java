package com.aif.language.sentence;

import com.aif.language.common.ISplitter;

import java.util.*;
import java.util.stream.Collectors;

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
        final Optional<List<Character>> optionalSeparators = sentenceSeparatorExtractor.extract(tokens);

        if (!optionalSeparators.isPresent()) {
            return new ArrayList<List<String>>(){{add(tokens);}};
        }

        final List<Character> separators = optionalSeparators.get();

        final List<Boolean> listOfPositions = mapToBooleans(tokens, separators);

        final SentenceIterator sentenceIterator = new SentenceIterator(tokens, listOfPositions);

        final List<List<String>> sentances = new ArrayList<>();
        while (sentenceIterator.hasNext()) {
            sentances.add(sentenceIterator.next());
        }

        sentances.forEach(sentence -> prepareSentences(sentence, separators));

        return sentances
                .parallelStream()
                .map(sentence -> SentenceSplitter.prepareSentences(sentence, separators))
                .collect(Collectors.toList());
    }

    private static List<String> prepareSentences(final List<String> sentence, final List<Character> separators) {
        final String lastToken = sentence.get(sentence.size() - 1);
        int index = lastToken.length() - 1;
        while (index > 0 && separators.contains(lastToken.charAt(index))) {
            index--;
        }
        index++;
        if (index < lastToken.length()) {
            final String leftToken = lastToken.substring(0, index);
            final String rightToken = lastToken.substring(index);
            final List<String> preparedSentance = new ArrayList<>(sentence.size() + 1);
            for (int i = 0; i < sentence.size() - 1; i++) {
                preparedSentance.add(sentence.get(i));
            }
            preparedSentance.add(leftToken);
            preparedSentance.add(rightToken);
            return preparedSentance;
        }
        return sentence;
    }

    private List<Boolean> mapToBooleans(final List<String> tokens, final List<Character> separators) {return tokens.stream()
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
