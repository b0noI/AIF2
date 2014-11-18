package io.aif.language.word.dict;


import io.aif.language.common.IExtractor;

import java.util.Optional;
import java.util.Set;

class FastRootTokenExtractor implements IExtractor<Set<String>, String>{

    @Override
    public Optional<String> extract(final Set<String> from) {
        if (from.isEmpty()) return Optional.empty();
        final double averageSize = from.stream()
                .mapToInt(String::length)
                .average()
                .getAsDouble();
        final double targetDelta = from.stream()
                .mapToDouble(token -> Math.abs(token.length() - averageSize))
                .min()
                .getAsDouble();
        return from.stream()
                .filter(token -> (double)token.length() - averageSize == targetDelta)
                .findFirst();
    }

}
