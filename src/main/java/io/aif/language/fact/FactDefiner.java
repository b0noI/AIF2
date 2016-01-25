package io.aif.language.fact;

import io.aif.language.ner.NERExtractor;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.Optional;

class FactDefiner implements IFactDefiner {

    private static final int NUM_PROPER_NOUNS_FOR_FACT = 1;

    private final int numProperNounsForFact;

    private final NERExtractor nerExtractor;

    public FactDefiner(final int numProperNounsForFact, final NERExtractor nerExtractor) {
        this.numProperNounsForFact = numProperNounsForFact;
        this.nerExtractor = nerExtractor;
    }

    public FactDefiner() {
        this(NUM_PROPER_NOUNS_FOR_FACT, new NERExtractor());
    }

    public FactDefiner(final int numProperNounsForFact) {
        this(numProperNounsForFact, new NERExtractor());
    }

    @Override
    public boolean isFact(final List<IWord> semanticSentence) {
        return (namedEntityCount(semanticSentence) >= numProperNounsForFact) ? true : false;
    }

    private long namedEntityCount(final List<IWord> semanticSentence) {
        return semanticSentence
                .stream()
                .map(nerExtractor::getNerType)
                .filter(Optional::isPresent)
                .count();
    }

}
