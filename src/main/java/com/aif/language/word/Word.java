package com.aif.language.word;

import java.util.List;

public abstract class Word {

    public abstract List<String> getTokens();

    public abstract long tokenCount(final String token);

    public abstract String basicToken();

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

        public Word getWord() {
            return Word.this;
        }

    }

}
