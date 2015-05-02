package io.aif.language.fact;

import io.aif.language.word.IWord;

import java.util.List;
import java.util.Set;

public interface IFact {

    public List<IWord> getSemanticSentence();

    public Set<IWord> getProperNouns();

    public boolean hasProperNoun(final IWord properNoun);

    public default boolean hasProperNouns(final Set<IWord> properNouns) {
        return properNouns
                .stream()
                .filter(this::hasProperNoun)
                .count() == properNouns.size();
    }

}
