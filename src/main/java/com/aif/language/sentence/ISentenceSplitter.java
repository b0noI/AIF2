package com.aif.language.sentence;

import java.util.List;

public interface ISentenceSplitter {

    public List<List<String>> parseSentences(List<String> tokens);

    public static enum Type {

        PREDEFINED(new PredefinedSentenceSplitter());

        private final ISentenceSplitter instance;

        private Type(final ISentenceSplitter instance) {
            this.instance = instance;
        }

        public ISentenceSplitter getInstance() {
            return instance;
        }

    }

}
