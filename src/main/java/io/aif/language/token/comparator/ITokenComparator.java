package io.aif.language.token.comparator;

import io.aif.language.common.settings.ISettings;

import java.util.*;

public interface ITokenComparator {

    public Double compare(String t1, String t2);

    public static ITokenComparator createComposite(final Collection<Map.Entry<ITokenComparator, Double>> comparators) {
        return new CompositeTokenComparator(comparators);
    }

    public static ITokenComparator defaultComparator() {
//        final Map<ITokenComparator, Double> comparators = new HashMap<>();
//        comparators.put(Type.RECURSIVE_SUBSTRING_COMPARATOR.getInstance(),
//                ISettings.SETTINGS.recursiveSubstringComparatorWeight());
//        comparators.put(Type.SIMPLE_TOKEN_COMPARATOR.getInstance(),
//                ISettings.SETTINGS.simpleTokenComparatorWeight());
//        comparators.put(Type.CHARACTER_DENSITY_COMPARATOR.getInstance(),
//                ISettings.SETTINGS.characterDensityComparatorWeight());
//        return createComposite(comparators.entrySet());
        return Type.CHARACTER_DENSITY_COMPARATOR.getInstance();
    }

    public static enum Type {
        SIMPLE_TOKEN_COMPARATOR(new SimpleTokenComparator()),
        RECURSIVE_SUBSTRING_COMPARATOR(new RecursiveSubstringComparator()),
        CHARACTER_DENSITY_COMPARATOR(new CharacterDensityTokenComparator());

        private final ITokenComparator instance;

        private Type(final ITokenComparator instance) { this.instance = instance; }

        public ITokenComparator getInstance() { return instance; }
    }
}