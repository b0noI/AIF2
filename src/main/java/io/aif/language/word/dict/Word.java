package io.aif.language.word.dict;


import io.aif.language.word.IWord;

import java.util.Set;

class Word implements IWord {

    private final Set<String> tokens;

    private final String rootToken;

    Word(final String rootToken, final Set<String> tokens) {
        this.tokens = tokens;
        this.rootToken = rootToken;
    }

    @Override
    public String getRootToken() {
        return rootToken;
    }

    @Override
    public Set<String> getAllTokens() {
        return tokens;
    }

    IWord merge(final IWord word) {
        final Word newWord = new Word(rootToken, tokens);
        newWord.tokens.addAll(word.getAllTokens());
        return newWord;
    }

}
