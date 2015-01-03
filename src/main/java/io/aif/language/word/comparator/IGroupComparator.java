package io.aif.language.word.comparator;

import io.aif.language.token.comparator.ITokenComparator;

import java.util.Collection;

public interface IGroupComparator {

    public double compare(final Collection<String> t1, final Collection<String> t2);

    public enum Type {
        PRIMITIVE(new PrimitiveComparator());

        private final IGroupComparator setComparator;

        Type(IGroupComparator setComparator) {
            this.setComparator = setComparator;
        }

        public IGroupComparator getComparator() {
            return setComparator;
        }

    }

    public static IGroupComparator createDefaultInstance(final ITokenComparator tokenComparator) {
        return new OptimisedMeshComparator(tokenComparator);
    }

}
