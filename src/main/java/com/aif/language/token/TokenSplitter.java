package com.aif.language.token;

import com.aif.language.common.ISplitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TokenSplitter implements ISplitter<String, String> {

    private final static    String                      REGEXP_TEMPLATE = "[%s]+";

    private final           ITokenSeparatorExtractor    tokenSeparatorExtractor;

    public TokenSplitter(final ITokenSeparatorExtractor tokenSeparatorExtractor) {
        this.tokenSeparatorExtractor = tokenSeparatorExtractor;
    }

    public TokenSplitter() {
        this(ITokenSeparatorExtractor.Type.PREDEFINED.getInstance());
    }

    @Override
    public List<String> split(final String txt) {
        final Optional<List<Character>> optionalSeparators = tokenSeparatorExtractor.extract(txt);

        if (!optionalSeparators.isPresent())
            return new ArrayList<String>(1){{add(txt);}};

        final List<Character> separators = optionalSeparators.get();
        final String regExp = TokenSplitter.prepareRegexp(separators);
        final List<String> tokens = Arrays.asList(txt.split(regExp));
        return TokenSplitter.filterIncorrectTokens(tokens);
    }

    private static List<String> filterIncorrectTokens(final List<String> tokens) {
        return tokens.stream()
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toList());
    }

    private static String prepareRegexp(final List<Character> separators) {
        final StringBuffer stringBuffer = new StringBuffer();
        separators.stream().forEach(separator -> stringBuffer.append(separator));
        return String.format(REGEXP_TEMPLATE, stringBuffer.toString());
    }

}
