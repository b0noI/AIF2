package com.aif.language.word;

import com.aif.language.comparator.ITokenComparator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Word extends AbstractWord {

    private static final double AVG_THRESHOLD = 0.75;

    private final ConcurrentMap<String, Long> tokensCountMap = new ConcurrentHashMap<>();
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
    public String basicToken() {
        Set<String> tokens = getTokens();
        long tokensCount = tokens.size();

        String lowestAvgToken = "";
        Double lowestAvg = Double.MAX_VALUE;

        double tmpAvg;
        for (String token : getTokens()) {
            tmpAvg = getTokens()
                        .stream()
                        .mapToDouble(tokenIn -> (token == tokenIn) ? 0 : comparator.compare(token, tokenIn))
                        .average()
                        .getAsDouble();

            if (lowestAvg > tmpAvg) {
                lowestAvg = tmpAvg;
                lowestAvgToken = token;
            }
        }

        return lowestAvgToken;
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
        //TODO: Should we not enforce the equal check here so that the words are merged only if they are equal
        that.getTokens()
            .forEach(token -> tokensCountMap.merge(token, that.tokenCount(token), (v1, v2) -> v1 + v2));
    }
}
