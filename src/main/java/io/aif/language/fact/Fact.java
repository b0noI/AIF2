package io.aif.language.fact;

import io.aif.language.semantic.weights.IProperNounCalculator;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Fact implements IFact {

    private final List<IWord> semanticSentence;

    public Fact(List<IWord> semanticSentence) {
        this.semanticSentence = semanticSentence;
    }

    public List<IWord> getSemanticSentence() {
        return semanticSentence;
    }

    public Set<IWord> getProperNouns() {
        //TODO The assumption here is that a sentence without a proper noun is not a fact.
        return FactHelper.getProperNouns(semanticSentence.stream())
                .collect(Collectors.toSet());
    }

    public boolean hasProperNoun(final IWord properNoun) {
        return getProperNouns().contains(properNoun);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (IWord n : semanticSentence)
            sb.append(n.toString());
        return sb.toString();
    }
}
