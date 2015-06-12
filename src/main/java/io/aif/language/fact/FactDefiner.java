package io.aif.language.fact;

import io.aif.common.FuzzyBoolean;
import io.aif.language.semantic.weights.IProperNounCalculator;
import io.aif.language.word.IWord;

import java.util.List;

class FactDefiner implements IFactDefiner {

    private static final int NUM_PROPER_NOUNS_FOR_FACT = 1;

    private final int numProperNounsForFact;

    private static final IProperNounCalculator properNounCalculator = IProperNounCalculator.getDefault();

    public FactDefiner(final int numProperNounsForFact) {
        this.numProperNounsForFact = numProperNounsForFact;
    }

    public FactDefiner() {
        this.numProperNounsForFact = NUM_PROPER_NOUNS_FOR_FACT;
    }

    @Override
    public boolean isFact(List<IWord> semanticSentence) {
        return (factCount(semanticSentence) >= numProperNounsForFact) ? true : false;
    }

    private static long factCount(final List<IWord> semanticSentence) {
        return semanticSentence
                .stream()
                .map(properNounCalculator::calculate)
                .map(FuzzyBoolean::new)
                .filter(FuzzyBoolean::isTrue)
                .count();
    }

}
