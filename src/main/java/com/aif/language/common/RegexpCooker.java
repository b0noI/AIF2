package com.aif.language.common;

import java.util.List;

public abstract class RegexpCooker {

    private final static String REGEXP_TEMPLATE = "[%s]+";

    public static String prepareRegexp(final List<Character> characters) {
        final StringBuffer stringBuffer = new StringBuffer();
        characters.stream().forEach(separator -> stringBuffer.append(characters));
        return String.format(REGEXP_TEMPLATE, stringBuffer.toString());
    }

}
