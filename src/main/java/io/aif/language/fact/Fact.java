package io.aif.language.fact;

import io.aif.language.semantic.weights.IProperNounCalculator;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Fact implements IFact {

    private Set<IWord> properNouns = null;

    private final List<IWord> semanticSentence;

    public Fact(List<IWord> semanticSentence) {
        this.semanticSentence = semanticSentence;
    }

    public List<IWord> getSemanticSentence() {
        return semanticSentence;
    }

    public Set<IWord> getProperNouns() {
        if (properNouns != null) return properNouns;
        //TODO The assumption here is that a sentence without a proper noun is not a fact.
        properNouns = FactHelper.getProperNouns(semanticSentence.stream())
                .collect(Collectors.toSet());
        return properNouns;
    }

    public boolean hasProperNoun(final IWord properNoun) {
        return getProperNouns().contains(properNoun);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        semanticSentence
                .stream()
                .map(IWord::getRootToken)
                .map(token -> {
                    if (isProperNoun(token)) return String.format("**%s** ", token);
                    return String.format("%s ", token);
                })
                .forEach(sb::append);
        return sb.toString();
    }

    private boolean isProperNoun(final String token) {
        return properNouns
                .stream()
                .filter(word -> word.getAllTokens().contains(token))
                .count() > 0;
    }

}
