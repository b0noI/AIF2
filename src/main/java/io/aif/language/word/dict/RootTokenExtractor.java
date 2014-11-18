package io.aif.language.word.dict;

import io.aif.language.common.IExtractor;
import io.aif.language.token.comparator.ITokenComparator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


class RootTokenExtractor implements IExtractor<Set<String>, String> {

    private final ITokenComparator comparator;

    RootTokenExtractor(ITokenComparator comparator) {
        this.comparator = comparator;
    }

    public Optional<String> extract(final Set<String> tokens) {
        if (tokens.isEmpty()) return Optional.empty();
        if (tokens.size() == 1) return Optional.of(tokens.stream().findAny().get());
        final Map<String, Double> results = new HashMap<>();

        tokens.parallelStream().map(token ->
            results.put(token, tokens
                    .stream()
                    .mapToDouble(tokenIn -> (token == tokenIn) ? 0 : comparator.compare(token, tokenIn))
                    .average()
                    .getAsDouble())
        );
        final double minValue = results.values().stream().min(Double::compare).get();

        return Optional.of(results.keySet()
                .stream()
                .filter(key -> results.get(key) == minValue)
                .findFirst()
                .get());
    }

}
