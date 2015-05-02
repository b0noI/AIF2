package io.aif.language.fact;


import io.aif.language.word.IWord;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IFactQuery {

    public Optional<List<List<IFact>>> findPath(final IWord properNoun1,
                                                final IWord properNoun2);

    public Optional<Set<IFact>> allFacts();

}
