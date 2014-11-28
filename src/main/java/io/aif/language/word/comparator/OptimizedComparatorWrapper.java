package io.aif.language.word.comparator;


import java.util.HashSet;
import java.util.Set;

class OptimizedComparatorWrapper implements ISetComparator {

    private final ISetComparator setComparator;

    OptimizedComparatorWrapper(ISetComparator setComparator) {
        this.setComparator = setComparator;
    }

    @Override
    public double compare(final Set<String> t1, final Set<String> t2) {
        final Set<String> lowerCaseSet1 = new HashSet<>();
        final Set<String> lowerCaseSet2 = new HashSet<>();
        t1.forEach(token -> lowerCaseSet1.add(token.toLowerCase()));
        t2.forEach(token -> lowerCaseSet2.add(token.toLowerCase()));
        final double primitiveResult = Type.PRIMITIVE.getComparator().compare(lowerCaseSet1, lowerCaseSet2);
        if (primitiveResult == 1.) return primitiveResult;
        if (primitiveResult == .0) return primitiveResult;
        return setComparator.compare(lowerCaseSet1, lowerCaseSet2);
    }

}
