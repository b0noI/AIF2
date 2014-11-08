package io.aif.language.word.dict;

import io.aif.language.word.IDict;
import io.aif.language.word.IWord;

import java.util.Set;

/**
 * Created by b0noI on 08/11/14.
 */
class Dict implements IDict {

    private final Set<IWord> words;

    Dict(final Set<IWord> words) {
        this.words = words;
    }

    @Override
    public Set<IWord> getWords() {
        return words;
    }

}
