package io.aif.language.fact;

import io.aif.language.word.IWord;

import java.util.List;
import java.util.Set;

public interface IFact {

    public List<IWord> getSemanticSentence();

    public Set<IWord> getNamedEntities();

    public boolean hasNamedEntity(final IWord properNoun);

    public default boolean hasNamedEntities(final Set<IWord> properNouns) {
        return properNouns
                .stream()
                .filter(this::hasNamedEntity)
                .count() == properNouns.size();
    }

}
