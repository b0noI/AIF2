package io.aif.language.fact;

import io.aif.language.ner.NERExtractor;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Fact implements IFact {

    private final List<IWord> semanticSentence;

    private final Set<IWord> namedEntities;

    public Fact(final List<IWord> semanticSentence) {
        this.semanticSentence = semanticSentence;
        namedEntities = semanticSentence.stream()
                .filter(word -> NERExtractor.getNerType(word).isPresent())
                .collect(Collectors.toSet());
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
        final StringBuilder sb = new StringBuilder();
        for (IWord n : semanticSentence)
            sb.append(n.toString());
        return sb.toString();
    }
}
