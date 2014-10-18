package com.aif.language.sentence.splitters;

import com.aif.language.common.VisibilityReducedForTestPurposeOnly;
import com.aif.language.sentence.separators.clasificators.ISentenceSeparatorGroupsClassificatory;
import com.aif.language.sentence.separators.extractors.ISeparatorExtractor;
import com.aif.language.sentence.separators.groupers.ISeparatorsGrouper;

import java.util.*;

class SimpleSentenceSplitter extends AbstractSentenceSplitter {

    public SimpleSentenceSplitter(final ISeparatorExtractor sentenceSeparatorExtractor,
                                  final ISeparatorsGrouper sentenceSeparatorsGrouper,
                                  final ISentenceSeparatorGroupsClassificatory sentenceSeparatorGroupsClassificatory) {
        super(sentenceSeparatorExtractor, sentenceSeparatorsGrouper, sentenceSeparatorGroupsClassificatory);
    }

    public SimpleSentenceSplitter() {
        this(ISeparatorExtractor.Type.PROBABILITY.getInstance(),
                ISeparatorsGrouper.Type.PROBABILITY.getInstance(),
                ISentenceSeparatorGroupsClassificatory.Type.PROBABILITY.getInstance());
    }

    @Override
    public List<Boolean> split(final List<String> tokens, final Map<ISentenceSeparatorGroupsClassificatory.Group, Set<Character>> splitters) {

        return SimpleSentenceSplitter.mapToBooleans(tokens, splitters.get(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1));

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

}
