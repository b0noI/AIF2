package com.aif.language.token;

import java.util.Arrays;
import java.util.List;

class RegexpTokeSplit implements ITokenSplit {

    private static final String REGEXP = "[ \n]+";

    @Override
    public List<String> parsTokens(final String txt) {
        return Arrays.asList(txt.split(REGEXP));
    }

}
