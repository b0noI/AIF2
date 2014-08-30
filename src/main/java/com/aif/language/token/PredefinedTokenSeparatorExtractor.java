package com.aif.language.token;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PredefinedTokenSeparatorExtractor implements ITokenSeparatorExtractor {

    private static final List<Character> SEPARATORS = Arrays.asList(new Character[]{' ', '\n'});

    @Override
    public Optional<List<Character>> extract(final String txt) {
        return Optional.of(SEPARATORS);
    }

}
