package io.aif.language.fact;

import io.aif.language.word.IWord;

import java.util.List;
import java.util.Set;

class Fact implements IFact {

    private Set<IWord> properNouns = null;

    private final List<IWord> semanticSentence;

    private final Set<IWord> namedEntities;

    public Fact(final List<IWord> semanticSentence, final Set<IWord> namedEntities) {
        this.semanticSentence = semanticSentence;
        this.namedEntities = namedEntities;
    }

    public List<IWord> getSemanticSentence() {
        return semanticSentence;
    }

    public Set<IWord> getNamedEntities() {
        //TODO The assumption here is that a sentence without a proper noun is not a fact.
        return namedEntities;
    }

    public boolean hasNamedEntity(final IWord properNoun) {
        return getNamedEntities().contains(properNoun);
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
