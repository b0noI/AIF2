package io.aif.language.sentence.separators.extractors;



import io.aif.language.common.IExtractor;

import java.util.List;

public interface ISeparatorExtractor extends IExtractor<List<String>, List<Character>> {

    public static enum Type {
        PREDEFINED(new PredefinedSeparatorExtractor()),
        PROBABILITY(new StatSeparatorExtractor());

        private final ISeparatorExtractor instance;

        private Type(final ISeparatorExtractor instance) {
            this.instance = instance;
        }

        public ISeparatorExtractor getInstance() {
            return instance;
        }

    }


}
