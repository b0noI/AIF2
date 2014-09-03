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

        final List<List<String>> sentences = new ArrayList<>();
        while (sentenceIterator.hasNext()) {
            sentences.add(sentenceIterator.next());
        }

        sentences.forEach(sentence -> prepareSentences(sentence, separators));

        return sentences
                .parallelStream()
                .map(sentence -> SentenceSplitter.prepareSentences(sentence, separators))
                .collect(Collectors.toList());
    }

    private static List<String> prepareSentences(final List<String> sentence, final List<Character> separators) {
        final List<String> preparedTokens = new ArrayList<>();

        for (String token: sentence) {
            preparedTokens.addAll(prepareToken(token, separators));
        }

        return preparedTokens;
    }

    private static List<String> prepareToken(final String token, final List<Character> separators) {
        final List<String> tokens = new ArrayList<>(3);
        boolean prevCharacterIsSeparator = separators.contains(token.charAt(0));
        int lastIndex = 0;
        for (int i = 1; i < token.length(); i++) {
            final boolean cuurentCharacterSeparator = separators.contains(token.charAt(i));
            if (prevCharacterIsSeparator != cuurentCharacterSeparator) {
                tokens.add(token.substring(lastIndex, i));
                lastIndex = i;
                prevCharacterIsSeparator = cuurentCharacterSeparator;
            }
        }
        tokens.add(token.substring(lastIndex));
        return tokens;
    }

    private List<Boolean> mapToBooleans(final List<String> tokens, final List<Character> separators) {
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
