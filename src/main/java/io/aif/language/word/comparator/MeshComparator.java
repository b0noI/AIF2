package io.aif.language.word.comparator;


import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IWord;

class MeshComparator implements IWordComparator {

    private final ITokenComparator tokenComparator;

    MeshComparator(ITokenComparator tokenComparator) {
        this.tokenComparator = tokenComparator;
    }

    @Override
    public double compare(final IWord o1, final IWord o2) {
        final Double sum = o1.getAllTokens()
                .stream()
                .mapToDouble(t1 -> o2.getAllTokens()
                                .stream()
                                .mapToDouble(t2 -> tokenComparator.compare(t1, t2))
                                .sum()
                )
                .sum();

        return sum / (o1.getAllTokens().size() * o2.getAllTokens().size());
    }

}
