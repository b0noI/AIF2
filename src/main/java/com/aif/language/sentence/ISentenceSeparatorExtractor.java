package com.aif.language.sentence;



import java.util.List;

public interface ISentenceSeparatorExtractor {

    public List<Character> getSeparators(final List<String> tokens);

    public static enum Type {
        PREDEFINED(new PredefinedSentenceSeparatorExtractor()),
        STAT(new StatSentenceSeparatorExtractor());

        private final ISentenceSeparatorExtractor instance;

        private Type(final ISentenceSeparatorExtractor instance) {
            this.instance = instance;
        }

        public ISentenceSeparatorExtractor getInstance() {
            return instance;
        }

    }


}
