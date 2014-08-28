package com.aif.language.token;

import com.aif.language.common.IExtractor;

public interface ITokenSeparatorExtractor extends IExtractor<String, Character> {

    public static enum Type {

        PREDEFINED(new PredefinedTokenSeparatorExtractor()),
        PROBABILITY(new ProbabilityBasedTokenSeparatorExtractor());

        private final ITokenSeparatorExtractor instance;

        private Type(final ITokenSeparatorExtractor instance) {
            this.instance = instance;
        }

        public ITokenSeparatorExtractor getInstance() {
            return instance;
        }

    }

}
