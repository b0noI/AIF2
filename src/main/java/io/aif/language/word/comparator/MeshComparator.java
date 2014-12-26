package io.aif.language.word.comparator;


import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IWord;

import java.util.Set;

class MeshComparator implements ISetComparator {

    private final ITokenComparator tokenComparator;

    MeshComparator(ITokenComparator tokenComparator) {
        this.tokenComparator = tokenComparator;
    }

    @Override
    public double compare(final Set<String> o1, final Set<String> o2) {
        final Double sum = o1
                .parallelStream()
                .mapToDouble(t1 -> o2
                                .stream()
                                .mapToDouble(t2 -> tokenComparator.compare(t1, t2))
                                .sum()
                )
                .sum();

        return sum / (o1.size() * o2.size());
    }

}
