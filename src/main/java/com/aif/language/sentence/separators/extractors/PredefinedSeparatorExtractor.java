package com.aif.language.sentence.separators.extractors;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PredefinedSeparatorExtractor implements ISeparatorExtractor {

    private static final List<Character> SEPARATORS = Arrays.asList(new Character[]{'.', '!', '?',
                                                                                    '(', ')', '[',
                                                                                    ']', '{', '}',
                                                                                    ';', '\'', '\"'});

    @Override
    public Optional<List<Character>> extract(List<String> tokens) {
        return Optional.of(SEPARATORS);
    }
}
