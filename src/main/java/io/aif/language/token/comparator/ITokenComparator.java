package io.aif.language.token.comparator;

import io.aif.language.common.settings.ISettings;

import java.util.*;

public interface ITokenComparator {

    public Double compare(String left, String right);

    public static ITokenComparator createComposite(final Collection<Map.Entry<ITokenComparator, Double>> comparators) {
        return new CompositeTokenComparator(comparators);
    }

    public static ITokenComparator defaultComparator() {
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