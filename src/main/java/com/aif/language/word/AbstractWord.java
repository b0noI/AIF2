package com.aif.language.word;

import java.util.Set;

public abstract class AbstractWord {

    public abstract Set<String> getTokens();

    public abstract long tokenCount(final String token);

    public abstract String basicToken();

    public abstract void merge(AbstractWord that);

    @Override
    public String toString() {
        return basicToken();
    }

    public class WordPlaceHolder {

        private final String token;

        public WordPlaceHolder(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public AbstractWord getWord() {
            return AbstractWord.this;
        }

    }
}
