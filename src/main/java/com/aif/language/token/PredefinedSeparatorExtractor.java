package com.aif.language.token;

import java.util.Arrays;
import java.util.List;

class PredefinedSeparatorExtractor implements ITokenSeparatorExtractor {

    private static final List<Character> SEPARATORS = Arrays.asList(new Character[]{' ', '\n'});

    @Override
    public List<Character> getSeparators(String txt) {
        return SEPARATORS;
    }

}
