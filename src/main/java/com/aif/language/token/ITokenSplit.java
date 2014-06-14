package com.aif.language.token;

import java.util.List;

public interface ITokenSplit {

    public List<String> parsTokens(final String txt);

    public static enum Type {

        REGEXP(new RegexpTokeSplit()),;

        private final ITokenSplit instance;

        private Type(final ITokenSplit instance) {
            this.instance = instance;
        }

        public ITokenSplit getInstance() {
            return instance;
        }

    }

}
