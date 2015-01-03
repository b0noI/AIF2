package io.aif.language.word.dict;


import io.aif.language.word.IWord;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class Word implements IWord {

    private final Set<String> tokens;

    private final String rootToken;

    private final Long count;

    //TODO What's the purpose of the count, assinging a LAME value to it.
    Word(final String rootToken, final Collection<String> tokens, final Long count) {
        this.tokens = new HashSet<>(tokens);
        this.rootToken = rootToken;
        this.count = count;
    }

    @Override
    public String getRootToken() {
        return rootToken;
    }

    @Override
    public Set<String> getAllTokens() {
        return tokens;
    }

    @Override
    public Long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return String.format("RootToken: [%s] tokens: [%s]",rootToken ,tokens);
    }

}
