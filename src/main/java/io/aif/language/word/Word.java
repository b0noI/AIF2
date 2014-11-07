package io.aif.language.word;

import io.aif.language.token.comparator.ITokenComparator;

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
    public Set<String> getAllTokens() {
        return tokensCountMap.keySet();
    }

    @Override
    public long tokenCount(String token) {
        return tokensCountMap.get(token);
    }

    @Override
    public String getRootToken() {
        Set<String> tokens = getAllTokens();
        long tokensCount = tokens.size();

        String lowestAvgToken = "";
        Double lowestAvg = Double.MAX_VALUE;

        double tmpAvg;
        for (String token : getAllTokens()) {
            tmpAvg = getAllTokens()
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
    public int hashCode() {
        return tokensCountMap.hashCode();
    }

    @Override
    public void merge(AbstractWord that) {
        //TODO: Should we not enforce the equal check here so that the words are merged only if they are equal
        that.getAllTokens()
            .forEach(token -> tokensCountMap.merge(token, that.tokenCount(token), (v1, v2) -> v1 + v2));
    }
}
