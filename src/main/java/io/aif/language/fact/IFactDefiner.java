package io.aif.language.fact;

import io.aif.language.word.IWord;

import java.util.List;

public interface IFactDefiner {

    // TODO Why can't we omit IWord
    public boolean isFact(List<IWord> semanticSentence);

    public enum Type {
        // TODO declare a default value for this in settings
        SIMPLE_FACT(new FactDefiner()),
        // TODO declare 2 in settings
        SUPER_FACT(new FactDefiner(2));

        private final IFactDefiner instance;

        private Type(final IFactDefiner instance) {
            this.instance = instance;
        }

        public IFactDefiner getInstance() {
            return instance;
        }
    }
}
