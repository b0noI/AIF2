package io.aif.language.word.dict;

import io.aif.language.common.IExtractor;
import io.aif.language.token.comparator.ITokenComparator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


class RootTokenExtractor implements IExtractor<Collection<String>, String> {

    private final ITokenComparator comparator;

    RootTokenExtractor(ITokenComparator comparator) {
        this.comparator = comparator;
    }

    public Optional<String> extract(final Collection<String> tokens) {
        if (tokens.isEmpty()) return Optional.empty();
        if (tokens.size() == 1) return Optional.of(tokens.stream().findAny().get());
        final Map<String, Double> results = new HashMap<>();

        // TODO This will compare a,b and b,a?
        tokens.forEach(
                token ->
                results.put(token,
                            tokens
                                    .stream()
                                    .mapToDouble(tokenIn -> (token == tokenIn) ? 1 : comparator.compare(token, tokenIn))
                                    .average()
                                    .getAsDouble())
        );



        final Optional<Double> minValueOpt = results.values().stream().min(Double::compare);

        if (!minValueOpt.isPresent()) {
            return Optional.empty();
        }
        final double minValue = minValueOpt.get();

        return Optional.of(results.keySet()
                .stream()
                .filter(key -> results.get(key) == minValue)
                .sorted(Comparator.comparing(String::length))
                .findFirst()
                .get());
    }

}
