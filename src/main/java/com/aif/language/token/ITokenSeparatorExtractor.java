package com.aif.language.token;

import java.util.List;

public interface ITokenSeparatorExtractor {

    public List<Character> getSeparators(final String txt);

    public static enum Type {

        PREDEFINED(new PredefinedSeparatorExtractor()),
        PROBABILITY(new ProbabilityBasedSeparatorExtractor());

        private final ITokenSeparatorExtractor instance;

        private Type(final ITokenSeparatorExtractor instance) {
            this.instance = instance;
        }

        public ITokenSeparatorExtractor getInstance() {
            return instance;
        }

    }

}
