package com.aif.language.sentence;

import com.aif.language.token.ITokenSeparatorExtractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andriikr on 14/06/2014.
 */
public class SentenceSplitter {

    private final ISentenceSeparatorExtractor sentenceSeparatorExtractor;

    public SentenceSplitter(final ISentenceSeparatorExtractor sentenceSeparatorExtractor) {
        this.sentenceSeparatorExtractor = sentenceSeparatorExtractor;
    }

    public SentenceSplitter() {
        this(ISentenceSeparatorExtractor.Type.PREDEFINED.getInstance());
    }

    public List<List<String>> parseSentences(List<String> tokens) {
        final List<Boolean> listOfPositions = mapToBooleans(tokens);

        final SentenceIterator sentenceIterator = new SentenceIterator(tokens, listOfPositions);

        final List<List<String>> sentances = new ArrayList<>();
        while (sentenceIterator.hasNext()) {
            sentances.add(sentenceIterator.next());
        }
        return sentances;
    }

    private List<Boolean> mapToBooleans(final List<String> tokens) {
        final String regex = SentenceSplitter.prepareRegex(sentenceSeparatorExtractor.getSeparators(tokens));
        return tokens.stream()
                .map(token -> token.matches(regex))
                .collect(Collectors.toList());
    }

    private static String prepareRegex(final List<Character> separators) {
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        separators.stream().forEach(separator -> stringBuffer.append(separator));
        stringBuffer.append("]+");
        return stringBuffer.toString();
    }

    private static class SentenceIterator implements Iterator<List<String>> {

        private final List<String>  tokens;

        private final List<Boolean> endTokens;

        private SentenceIterator(List<String> tokens, List<Boolean> endTokens) {
            this.tokens = tokens;
            this.endTokens = endTokens;
        }

        @Override
        public boolean hasNext() {
            return tokens.isEmpty();
        }

        @Override
        public List<String> next() {
            final List<String> sentence = getNextSentence();
            this.removeNLeftElements(sentence.size());

            return sentence;
        }

        private List<String> getNextSentence() {
            final int index = this.endTokens.indexOf(true);
            final int endIndex = index == -1 ? this.tokens.size() - 1 : index;
            return this.tokens.subList(0, endIndex + 1);
        }

        private void removeNLeftElements(final int count) {
            final int newSzie = this.tokens.size() - count;
            while (this.tokens.size() != newSzie) {
                this.tokens.remove(0);
                this.endTokens.remove(0);
            }
        }

    }

}
