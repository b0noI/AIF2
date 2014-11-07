package io.aif.language.word.dict;

import io.aif.language.token.comparator.ITokenComparator;

import java.util.Set;

/**
 * Created by vsk on 11/7/14.
 */
class RootTokenExtractor {

    private final ITokenComparator comparator;

    RootTokenExtractor(ITokenComparator comparator) {
        this.comparator = comparator;
    }

    public String extract(final Set<String> tokens) {
        String lowestAvgToken = "";
        Double lowestAvg = Double.MAX_VALUE;

        for (String token : tokens) {
            final double tmpAvg = tokens
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

}
