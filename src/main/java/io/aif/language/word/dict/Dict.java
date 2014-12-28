package io.aif.language.word.dict;

import io.aif.language.word.IDict;
import io.aif.language.word.IWord;

import java.util.Set;


class Dict implements IDict {

    private final Set<IWord> words;

    Dict(final Set<IWord> words) {
        this.words = words;
    }

    @Override
    public Set<IWord> getWords() {
        return words;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        getWords()
                .stream()
                .sorted((w1, w2) -> w1.getRootToken().compareTo(w2.getRootToken()))
                .forEach(word -> stringBuilder.append(String.format("%s\n", word.toString())));
        return stringBuilder.toString();
    }

}
