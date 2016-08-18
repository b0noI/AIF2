package io.aif.language.token;

import io.aif.language.common.IExtractor;

import java.util.List;

// TODO(#251): Extract TokenSeparatorExtractor classes into separated package.
public interface ITokenSeparatorExtractor extends IExtractor<String, List<Character>> {

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
