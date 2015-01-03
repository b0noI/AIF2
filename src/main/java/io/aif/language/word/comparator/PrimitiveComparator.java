package io.aif.language.word.comparator;

import java.util.Collection;

class PrimitiveComparator implements IGroupComparator {

    private static final double THRESHOLD = .45;

    @Override
    public double compare(Collection<String> t1, Collection<String> t2) {
        if (t1.isEmpty() || t2.isEmpty()) return .0;
        if (t1.stream().filter(t2::contains).count() > 0) return 1.0;
        final double averageSize1 = t1.stream().mapToInt(String::length).average().getAsDouble();
        final double averageSize2 = t2.stream().mapToInt(String::length).average().getAsDouble();
        final double delta = 1. - Math.min(averageSize1, averageSize2) / Math.max(averageSize1, averageSize2);
        if (delta > THRESHOLD) return .0;
        return .5;
    }
}
