package io.aif.language.ner;

import io.aif.language.word.IWord;

import java.util.Arrays;
import java.util.Optional;

public class NERExtractor {

    public Optional<Type> getNerType(final IWord word) {
        return Arrays.stream(Type.values())
                .filter(nerType -> nerType.contains(word).isTrue())
                .findAny();
    }
}
