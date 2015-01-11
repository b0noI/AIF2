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

        /*tokens.stream().map(token ->
            results.put(token, tokens
                    .stream()
                            //with similar characters.
                    .mapToDouble(tokenIn -> (token == tokenIn) ? 0 : comparator.compare(token, tokenIn))
                    .average()
                    .getAsDouble())
        );*/

        // TODO This will compare a,b and b,a?
        tokens.forEach(
                token ->
                results.put(token,
                            tokens
                                    .stream()
                                    //TODO Why return 0 if tokens are the same, the comparator returns large values
                                    //for tokens with similar characters.
                                    .mapToDouble(tokenIn -> (token == tokenIn) ? 0 : comparator.compare(token, tokenIn))
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
                .sorted((str1, str2) ->
                    ((Integer) str2.length()).compareTo(str1.length())
                )
                .findFirst()
                .get());
    }

}
