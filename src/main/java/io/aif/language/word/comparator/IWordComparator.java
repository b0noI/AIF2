package io.aif.language.word.comparator;

import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IWord;

import java.util.Comparator;

public interface IWordComparator {

    public double compare(IWord t1, IWord t2);

    public static IWordComparator createDefaultInstance(final ITokenComparator tokenComparator) {
        return new MeshComparator(tokenComparator);
    }

}
