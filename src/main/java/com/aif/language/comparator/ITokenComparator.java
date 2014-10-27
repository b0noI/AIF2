package com.aif.language.comparator;

public interface ITokenComparator {

    public Double compare(String t1, String t2);

    public static enum Type {
        SIMPLETOKENCOMPARATOR           (new SimpleTokenComparator()),
        RECURSIVESUBSTRINGCOMPARATOR    (new RecursiveSubstringComparator()),
        COMPOSITETOKENCOMPARATOR        (new CompositeTokenComparator());

        private final ITokenComparator instance;

        private Type(final ITokenComparator instance) { this.instance = instance; }

        public ITokenComparator getInstance() { return instance; }
    }
}
