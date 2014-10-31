package com.aif.language.token.comparator;

import java.util.*;
import java.util.stream.Collectors;

public interface ITokenComparator {

    public Double compare(String t1, String t2);

    public static ITokenComparator createComposite(final Collection<Map.Entry<ITokenComparator, Double>> comparators) {
        return new CompositeTokenComparator(comparators);
    }

    public static ITokenComparator defaultComparator() {

        final List<Map.Entry<ITokenComparator, Double>> comparators = Arrays.asList(Type.values())
                .stream()
                .map(comparator -> new AbstractMap.SimpleEntry<ITokenComparator, Double>(comparator.getInstance(), 1.))
                .collect(Collectors.toList());
        return createComposite(comparators);
    }

    public static enum Type {
        SIMPLETOKENCOMPARATOR           (new SimpleTokenComparator()),
        RECURSIVESUBSTRINGCOMPARATOR    (new RecursiveSubstringComparator());

        private final ITokenComparator instance;

        private Type(final ITokenComparator instance) { this.instance = instance; }

        public ITokenComparator getInstance() { return instance; }
    }
}