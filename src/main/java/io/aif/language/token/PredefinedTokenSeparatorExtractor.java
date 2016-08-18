package io.aif.language.token;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// TODO(#250): PredefinedTokenSeparatorExtractor should have list of the characters in
// the config (and not hardcoded).
class PredefinedTokenSeparatorExtractor implements ITokenSeparatorExtractor {

    // TODO(#254): Revisit default list of token separators.
    private static final List<Character> SEPARATORS
            = Arrays.asList(
                    new Character[]{' ', '\n', '\t', System.lineSeparator().charAt(0)});

    @Override
    public Optional<List<Character>> extract(final String txt) {
        return Optional.of(SEPARATORS);
    }

}
