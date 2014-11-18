package io.aif.language.word.comparator;

import io.aif.language.token.comparator.ITokenComparator;

import java.util.Set;

public interface ISetComparator {

    public double compare(final Set<String> t1, final Set<String> t2);

    public static ISetComparator createDefaultInstance(final ITokenComparator tokenComparator) {
        return new OptimizedComparatorWrapper(new MeshComparator(tokenComparator));
    }

}
