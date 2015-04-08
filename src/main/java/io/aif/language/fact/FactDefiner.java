package io.aif.language.fact;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.word.IWord;

import java.util.List;

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
    public boolean isFact(List<ISemanticNode<IWord>> semanticSentence) {
        return (factCount(semanticSentence) >= numProperNounsForFact) ? true : false;
    }

    private static long factCount(final List<ISemanticNode<IWord>> semanticSentence) {
        return semanticSentence
                .stream()
                .filter(node -> node.isProperNoun().isTrue())
                .count();
    }
}
