package com.aif.language.token;

import com.aif.language.common.ISplitter;
import com.aif.language.common.RegexpCooker;
import com.aif.language.common.VisibilityReducedForTestPurposeOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TokenSplitter implements ISplitter<String, String> {

    private final   RegexpCooker                regexpCooker;

    private final   ITokenSeparatorExtractor    tokenSeparatorExtractor;

    public TokenSplitter(final ITokenSeparatorExtractor tokenSeparatorExtractor) {
        this(tokenSeparatorExtractor, new RegexpCooker());
    }

    public TokenSplitter() {
        this(ITokenSeparatorExtractor.Type.PREDEFINED.getInstance(), new RegexpCooker());
    }

    @VisibilityReducedForTestPurposeOnly
    TokenSplitter(final ITokenSeparatorExtractor tokenSeparatorExtractor, final RegexpCooker regexpCooker) {
        this.tokenSeparatorExtractor = tokenSeparatorExtractor;
        this.regexpCooker = regexpCooker;
    }

    @Override
    public List<String> split(final String txt) {
        final Optional<List<Character>> optionalSeparators = tokenSeparatorExtractor.extract(txt);

        if (!optionalSeparators.isPresent())
            return new ArrayList<String>(1){{add(txt);}};

        final List<Character> separators = optionalSeparators.get();
        final String regExp = regexpCooker.prepareRegexp(separators);
        final List<String> tokens = Arrays.asList(txt.split(regExp));
        return TokenSplitter.filterIncorrectTokens(tokens);
    }

    @VisibilityReducedForTestPurposeOnly
    static List<String> filterIncorrectTokens(final List<String> tokens) {
        return tokens.stream()
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toList());
    }

}
