package com.aif.language.sentence.separators.extractors;



import com.aif.language.common.IExtractor;

import java.util.List;

public interface ISentenceSeparatorExtractor extends IExtractor<List<String>, List<Character>> {

    public static enum Type {
        PREDEFINED(new PredefinedSentenceSeparatorExtractor()),
        PROBABILITY(new RecursiveStatSentenceSeparatorExtractor());

        private final ISentenceSeparatorExtractor instance;

        private Type(final ISentenceSeparatorExtractor instance) {
            this.instance = instance;
        }

        public ISentenceSeparatorExtractor getInstance() {
            return instance;
        }

    }


}
