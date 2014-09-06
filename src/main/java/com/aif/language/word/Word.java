package com.aif.language.word;

import java.util.*;

public class Word extends AbstractWord {

    private static final double AVG_THRESHOLD = 0.75;
    private final Map<String, Long> tokensCount = new HashMap<>();
    private final ITokenComparator comparator;

    public Word(String token, ITokenComparator comparator) {
        tokensCount.put(token, (long) 1);
        this.comparator = comparator;
    }

    @Override
    public Set<String> getTokens() {
        return tokensCount.keySet();
    }

    @Override
    public long tokenCount(String token) {
        return tokensCount.get(token);
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
        Double sum = this.getTokens().stream()
                .mapToDouble(
                    thisToken -> that.getTokens().stream()
                            .mapToDouble(
                                    thatToken -> comparator.compare(thisToken, thatToken)
                            ).sum()
                ).sum();
        Double avg = sum / (this.getTokens().size() * that.getTokens().size());
        return (avg > AVG_THRESHOLD);
    }

    @Override
    public int hashCode() {
        return tokensCount.hashCode();
    }

    @Override
    public void merge(AbstractWord that) {
        that.getTokens().forEach(
                thatToken -> tokensCount.put(thatToken, that.tokenCount(thatToken) +
                (tokensCount.containsKey(thatToken) ? tokensCount.get(thatToken) : 0l))
        );
    }
}
