package io.aif.language.ner;


import io.aif.language.word.IWord;

import java.util.Optional;

public class NERExtractor {

    public Optional<Type> getNerType(final IWord word) {
        for (Type nerType : Type.values()) {
            if (nerType.contains(word).isTrue()) {
                return Optional.of(nerType);
            }
        }
        return Optional.empty();
    }

}
