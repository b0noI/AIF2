package io.aif.language.token;

import com.google.common.annotations.VisibleForTesting;
import io.aif.language.common.IRegexpCooker;
import io.aif.language.common.ISplitter;
import io.aif.language.common.RegexpCooker;
import io.aif.language.token.separator.ITokenSeparatorExtractor;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Tokenizer implements ISplitter<String, String> {

    private static  final   Logger                      logger = Logger.getLogger(Tokenizer.class);

    private         final   IRegexpCooker               regexpCooker;

    private         final   ITokenSeparatorExtractor    tokenSeparatorExtractor;

    public Tokenizer(final ITokenSeparatorExtractor tokenSeparatorExtractor) {
        this(tokenSeparatorExtractor, new RegexpCooker());
    }

    public Tokenizer() {
        this(ITokenSeparatorExtractor.Type.PREDEFINED.getInstance(), new RegexpCooker());
    }

    @VisibleForTesting
    Tokenizer(final ITokenSeparatorExtractor tokenSeparatorExtractor, final IRegexpCooker regexpCooker) {
        this.tokenSeparatorExtractor = tokenSeparatorExtractor;
        this.regexpCooker = regexpCooker;
    }

    @Override
    public List<String> split(final String txt) {
        logger.debug(String.format("Starting splitting text with size %d", txt.length()));
        final Optional<List<Character>> optionalSeparators = tokenSeparatorExtractor.extract(txt);

        if (!optionalSeparators.isPresent() || optionalSeparators.get().isEmpty()) {
            logger.error("Failed to extract token splitter character, returning text");
            return new ArrayList<String>(1) {{
                add(txt);
            }};
        }

        final List<Character> separators = optionalSeparators.get();
        logger.debug(String.format("Token separators: %s", Arrays.toString(separators.toArray())));
        final String regExp = regexpCooker.prepareRegexp(separators);
        logger.debug(String.format("Regexp for toke splitting: %s", regExp));
        final List<String> tokens = Arrays.asList(txt.split(regExp));
        logger.debug(String.format("Tokens found (before filtering): %d", tokens.size()));
        return Tokenizer.filterIncorrectTokens(tokens);
    }

    @VisibleForTesting
    static List<String> filterIncorrectTokens(final List<String> tokens) {
        return tokens.stream()
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toList());
    }

}
