package io.aif.language.token.separator;

import io.aif.language.common.settings.ISettings;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

// TODO(#250): PredefinedTokenSeparatorExtractor should have list of the characters in
// the config (and not hardcoded).
class PredefinedTokenSeparatorExtractor implements ITokenSeparatorExtractor {

    // TODO(#254): Revisit default list of token separators.
    private final List<Character> separators;

    PredefinedTokenSeparatorExtractor(ISettings settings) {
        this.separators = Stream.of(settings.predefinedSeparators().split(""))
                                .map(it -> it.charAt(0)).collect(toList());
    }

    @Override
    public Optional<List<Character>> extract(final String txt) {
        return Optional.of(separators);
    }
}
