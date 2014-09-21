package com.aif.language.word;

import java.util.*;

public class Word extends AbstractWord {

    private static final double AVG_THRESHOLD = 0.75;

    private final Map<String, Long> tokensCountMap = new HashMap<>();
    private final ITokenComparator comparator;

    public Word(String token, ITokenComparator comparator) {
        tokensCountMap.put(token, (long) 1);
        this.comparator = comparator;
    }

    @Override
    public Set<String> getTokens() {
        return tokensCountMap.keySet();
    }

    @Override
    public long tokenCount(String token) {
        return tokensCountMap.get(token);
    }

    @Override
    //TODO: What should this method return?
    public String basicToken() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word that = (Word) o;
        Double sum = getTokens()
                .stream()
                .mapToDouble(t1 -> that.getTokens()
                                .stream()
                                .mapToDouble(t2 -> comparator.compare(t1, t2))
                                .sum()
                )
                .sum();

        Double avg = sum / (this.getTokens().size() * that.getTokens().size());
        return (avg > AVG_THRESHOLD);
    }

    @Override
    public int hashCode() {
        return tokensCountMap.hashCode();
    }

    @Override
    public void merge(AbstractWord that) {
        that.getTokens()
            .forEach(token -> {
                long count = tokensCountMap.getOrDefault(token, 0l) + that.tokenCount(token);
                tokensCountMap.put(token, count);
            });
    }
}
