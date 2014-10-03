package com.aif.language.sentence.splitters;

import com.aif.language.common.ISplitter;
import com.aif.language.common.VisibilityReducedForTestPurposeOnly;
import com.aif.language.sentence.separators.clasificators.ISentenceSeparatorGroupsClassificatory;
import com.aif.language.sentence.separators.extractors.ISentenceSeparatorExtractor;
import com.aif.language.sentence.separators.groupers.ISentenceSeparatorsGrouper;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractSentenceSplitter implements ISplitter<List<String>, List<String>> {

    private static  final Logger                                    logger                                  = Logger.getLogger(AbstractSentenceSplitter.class)  ;

    private         final ISentenceSeparatorExtractor               sentenceSeparatorExtractor                                                                  ;

    private         final ISentenceSeparatorsGrouper                sentenceSeparatorsGrouper                                                                   ;

    private         final ISentenceSeparatorGroupsClassificatory    sentenceSeparatorGroupsClassificatory                                                       ;

    protected AbstractSentenceSplitter(final ISentenceSeparatorExtractor sentenceSeparatorExtractor,
                                       final ISentenceSeparatorsGrouper sentenceSeparatorsGrouper,
                                       final ISentenceSeparatorGroupsClassificatory sentenceSeparatorGroupsClassificatory) {
        this.sentenceSeparatorExtractor = sentenceSeparatorExtractor;
        this.sentenceSeparatorsGrouper = sentenceSeparatorsGrouper;
        this.sentenceSeparatorGroupsClassificatory = sentenceSeparatorGroupsClassificatory;
    }

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

        final List<List<String>> sentences = split(tokens, separatorsGroupsClassified);

        return sentences
                .parallelStream()
                .map(sentence -> SimpleSentenceSplitter.prepareSentences(sentence, separators))
                .collect(Collectors.toList());
    }

    public static Logger getLogger() {
        return logger;
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

    public abstract List<List<String>> split(final List<String> target, final Map<ISentenceSeparatorGroupsClassificatory.Group, Set<Character>> splitters);

    public enum Type {

        SIMPLE(new SimpleSentenceSplitter()),
        HEURISTIC(new HeuristicSentenceSplitter());

        private final AbstractSentenceSplitter instance;

        Type(AbstractSentenceSplitter instance) {
            this.instance = instance;
        }

        public AbstractSentenceSplitter getInstance() {
            return instance;
        }

    }

}
