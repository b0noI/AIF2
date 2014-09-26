package com.aif.language.sentence;

import com.aif.language.common.ISplitter;
import com.aif.language.common.VisibilityReducedForTestPurposeOnly;
import com.aif.language.sentence.separators.clasificators.ISentenceSeparatorGroupsClassificatory;
import com.aif.language.sentence.separators.extractors.ISentenceSeparatorExtractor;
import com.aif.language.sentence.separators.groupers.ISentenceSeparatorsGrouper;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class SentenceSplitter implements ISplitter<List<String>, List<String>> {

    private static  final   Logger                                  logger                                  = Logger.getLogger(SentenceSplitter.class)  ;

    private         final   ISentenceSeparatorExtractor             sentenceSeparatorExtractor                                                          ;

    private         final   ISentenceSeparatorsGrouper              sentenceSeparatorsGrouper                                                           ;

    private         final ISentenceSeparatorGroupsClassificatory    sentenceSeparatorGroupsClassificatory                                               ;

    public SentenceSplitter(final ISentenceSeparatorExtractor sentenceSeparatorExtractor,
                            final ISentenceSeparatorsGrouper sentenceSeparatorsGrouper,
                            final ISentenceSeparatorGroupsClassificatory sentenceSeparatorGroupsClassificatory) {
        this.sentenceSeparatorExtractor = sentenceSeparatorExtractor;
        this.sentenceSeparatorsGrouper = sentenceSeparatorsGrouper;
        this.sentenceSeparatorGroupsClassificatory = sentenceSeparatorGroupsClassificatory;
    }

    public SentenceSplitter() {
        this(ISentenceSeparatorExtractor.Type.PROBABILITY.getInstance(),
                ISentenceSeparatorsGrouper.Type.PROBABILITY.getInstance(),
                ISentenceSeparatorGroupsClassificatory.Type.PROBABILITY.getInstance());
    }

    @Override
    public List<List<String>> split(final List<String> tokens) {
        logger.debug(String.format("Starting sentence extraction for tokens: %d", tokens.size()));
        final Optional<List<Character>> optionalSeparators = sentenceSeparatorExtractor.extract(tokens);

        if (!optionalSeparators.isPresent() || optionalSeparators.get().size() == 0) {
            logger.error("Fail to extract any sentence separators, returning tokens");
            return new ArrayList<List<String>>(){{add(tokens);}};
        }

        final List<Character> separators = optionalSeparators.get();
        logger.debug(String.format("Sentences separators in this text: %s", Arrays.toString(separators.toArray())));

        final List<Set<Character>> separatorsGroups = sentenceSeparatorsGrouper.group(tokens, separators);

        final Map<ISentenceSeparatorGroupsClassificatory.Group, Set<Character>> separatorsGroupsClassified = sentenceSeparatorGroupsClassificatory.classify(tokens, separatorsGroups);

        final List<Boolean> listOfPositions = SentenceSplitter.mapToBooleans(tokens, separatorsGroupsClassified.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1));

        final SentenceIterator sentenceIterator = new SentenceIterator(tokens, listOfPositions);

        final List<List<String>> sentences = new ArrayList<>();
        while (sentenceIterator.hasNext()) {
            sentences.add(sentenceIterator.next());
        }

        logger.debug(String.format("Founded %d sentences", sentences.size()));

        return sentences
                .parallelStream()
                .map(sentence -> SentenceSplitter.prepareSentences(sentence, separators))
                .collect(Collectors.toList());
    }

    @VisibilityReducedForTestPurposeOnly
    static List<String> prepareSentences(final List<String> sentence, final List<Character> separators) {
        final List<String> preparedTokens = new ArrayList<>();

        for (String token: sentence) {
            preparedTokens.addAll(prepareToken(token, separators));
        }

        return preparedTokens;
    }

    @VisibilityReducedForTestPurposeOnly
    static List<String> prepareToken(final String token, final List<Character> separators) {
        final List<String> tokens = new ArrayList<>(3);
        final int lastPosition = lastNonSeparatorPosition(token, separators);
        final int firstPosition = firstNonSeparatorPosition(token, separators);

        if (firstPosition != 0) {
            tokens.add(token.substring(0, firstPosition));
        }

        tokens.add(token.substring(firstPosition, lastPosition));

        if (lastPosition != token.length()) {
            tokens.add(token.substring(lastPosition, token.length()));
        }

        return tokens;
    }

    @VisibilityReducedForTestPurposeOnly
    static int firstNonSeparatorPosition(final String token, final List<Character> separarors) {
        if (!separarors.contains(token.charAt(0))) {
            return 0;
        }
        int i = 0;
        while (i < token.length() && separarors.contains(token.charAt(i))) {
            i++;
        }
        if (i == token.length()) {
            return 0;
        }
        return i;
    }

    @VisibilityReducedForTestPurposeOnly
    static int lastNonSeparatorPosition(final String token, final List<Character> separators) {
        if (!separators.contains(token.charAt(token.length() - 1))) {
            return token.length();
        }
        int i = token.length() - 1;
        while (i > 0 && separators.contains(token.charAt(i))) {
            i--;
        }
        i++;
        if (i == 0) {
            return token.length();
        }
        return i;
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
