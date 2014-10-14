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

        public AbstractWord getWord() { return AbstractWord.this; }

        @Override
        public String toString() { return token; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WordPlaceHolder that = (WordPlaceHolder) o;

            if (token != null ? !token.equals(that.token) : that.token != null) return false;

            return true;
        }
    }
}
