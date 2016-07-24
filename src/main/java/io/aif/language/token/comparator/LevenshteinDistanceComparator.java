package io.aif.language.token.comparator;

import org.apache.commons.lang3.StringUtils;

class LevenshteinDistanceComparator implements ITokenComparator {

    @Override
    public Double compare(String left, String right) {
        final int biggestLength = Math.max(left.length(), right.length());
        return (double)StringUtils.getLevenshteinDistance(left, right)
                / (double)biggestLength;
    }

}
