package com.aif.language.sentence.separators.extractors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class RecursiveStatSeparatorExtractor extends StatSeparatorExtractor {

    @Override
    public Optional<List<Character>> extract(List<String> tokens) {

        final List<String> tokensCopy = new ArrayList<>(tokens);

        final Optional<List<Character>> optSeparators = super.extract(tokensCopy);
        if (!optSeparators.isPresent()) {
            return Optional.empty();
        }
        if (optSeparators.get().isEmpty()) {
            return Optional.empty();
        }
        final List<Character> separators = optSeparators.get();
        final List<String> filteredTokens = filter(tokensCopy, separators);

        final List<Character> nextSeparators = super.extract(filteredTokens).get();

        for (int i = 0; i < (int)((double)nextSeparators.size() * .75); i++) {
            separators.add(nextSeparators.get(i));
        }

        return Optional.of(separators);
    }

    private List<String> filter(final List<String> tokens, final List<Character> splitters) {
        final List<String> filteredTokens = new ArrayList<>(tokens.size());
        for (int i = 0; i < tokens.size(); i++) {
            filteredTokens.add(filter(tokens.get(i), splitters));
        }
        return filteredTokens
                .stream()
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toList());
    }

    private String filter(final String token, final List<Character> splitters) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (Character ch : token.toCharArray()) {
            if (!splitters.contains(ch)) {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }

}
