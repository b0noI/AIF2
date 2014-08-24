package com.aif.language.sentence;

import com.aif.language.token.ITokenSeparatorExtractor;

import java.util.Arrays;
import java.util.List;

class PredefinedSentenceSeparatorExtractor implements ISentenceSeparatorExtractor {

    private static final List<Character> SEPARATORS = Arrays.asList(new Character[]{'.', '!', '?',
                                                                                    '(', ')', '[',
                                                                                    ']', '{', '}',
                                                                                    ';', '\'', '\"'});

    @Override
    public List<Character> getSeparators(List<String> tokens) {
        return SEPARATORS;
    }
}
