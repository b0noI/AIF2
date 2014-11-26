package io.aif.language.token.comparator;

import java.util.*;
import java.util.stream.Collectors;

public interface ITokenComparator {

    public Double compare(String t1, String t2);

    public static ITokenComparator createComposite(final Collection<Map.Entry<ITokenComparator, Double>> comparators) {
        return new CompositeTokenComparator(comparators);
    }

    public static ITokenComparator defaultComparator() {
        final Map<ITokenComparator, Double> comparators = new HashMap<>();
        comparators.put(Type.SIMPLETOKENCOMPARATOR.getInstance(), .8);
        comparators.put(Type.RECURSIVESUBSTRINGCOMPARATOR.getInstance(), 1.);
        return createComposite(comparators.entrySet());
    }

    public static enum Type {
        SIMPLETOKENCOMPARATOR           (new SimpleTokenComparator()),
        RECURSIVESUBSTRINGCOMPARATOR    (new RecursiveSubstringComparator());

        private final ITokenComparator instance;

        private Type(final ITokenComparator instance) { this.instance = instance; }

        public ITokenComparator getInstance() { return instance; }
    }
}