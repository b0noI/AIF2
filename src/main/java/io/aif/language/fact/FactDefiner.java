package io.aif.language.fact;

import io.aif.fuzzy.FuzzyBoolean;
import io.aif.language.ner.NERExtractor;
import io.aif.language.ner.noun.IProperNounSet;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.Optional;

class FactDefiner implements IFactDefiner {

    private static final int NUM_PROPER_NOUNS_FOR_FACT = 1;

    private final int numProperNounsForFact;

    public FactDefiner(final int numProperNounsForFact) {
        this.numProperNounsForFact = numProperNounsForFact;
    }

    public FactDefiner() {
        this.numProperNounsForFact = NUM_PROPER_NOUNS_FOR_FACT;
    }

    @Override
    public boolean isFact(final List<IWord> semanticSentence) {
        return (namedEntityCount(semanticSentence) >= numProperNounsForFact) ? true : false;
    }

    private static long namedEntityCount(final List<IWord> semanticSentence) {
        return semanticSentence
                .stream()
                .map(NERExtractor::getNerType)
                .filter(Optional::isPresent)
                .count();
    }

}
