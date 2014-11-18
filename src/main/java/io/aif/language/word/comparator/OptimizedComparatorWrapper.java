package io.aif.language.word.comparator;


import java.util.Set;

class OptimizedComparatorWrapper implements ISetComparator {

    private static final double THRESHOLD = .3;

    private final ISetComparator setComparator;

    OptimizedComparatorWrapper(ISetComparator setComparator) {
        this.setComparator = setComparator;
    }

    @Override
    public double compare(final Set<String> t1, final Set<String> t2) {
        if (t1.isEmpty() || t2.isEmpty()) return .0;
        if (t1.stream().filter(t2::contains).count() > 0) return 1.0;
        final double averageSize1 = t1.stream().mapToInt(String::length).average().getAsDouble();
        final double averageSize2 = t2.stream().mapToInt(String::length).average().getAsDouble();
        final double delta = 1. - Math.min(averageSize1, averageSize2) / Math.max(averageSize1, averageSize2);
        if (delta > THRESHOLD) return .0;
        return setComparator.compare(t1, t2);
    }

}
