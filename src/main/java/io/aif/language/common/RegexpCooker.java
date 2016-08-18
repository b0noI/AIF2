package io.aif.language.common;

import java.util.List;

// TODO(#249): find if the class can be replaced with
// some open source analog.
public class RegexpCooker {

    private final static String REGEXP_TEMPLATE = "%s[%s]+%s";

    private final String prefix;

    private final String postfix;

    public RegexpCooker(String prefix, String postfix) {
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public RegexpCooker() {
        this("", "");
    }

    public String prepareRegexp(final List<Character> characters) {
        final StringBuffer stringBuffer = new StringBuffer();
        characters.stream().forEach(separator -> stringBuffer.append(RegexpCooker.prepareCharacter(separator)));
        return String.format(REGEXP_TEMPLATE, prefix, stringBuffer.toString(), postfix);
    }

    private static String prepareCharacter(final Character ch) {
        switch (ch) {
            case '.':
            case '\\':
            case '\'':
            case '\"':
            case ']':
            case '[':
            case '(':
            case ')':
                return "\\" + ch;
            default:
                return "" + ch;
        }
    }

}
