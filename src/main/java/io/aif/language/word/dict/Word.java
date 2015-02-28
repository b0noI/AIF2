package io.aif.language.word.dict;

import io.aif.language.word.IWord;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


class Word implements IWord {

    private final Set<String> tokens;

    private final String rootToken;

    private final Long count;

    Word(final String rootToken, final Collection<String> tokens, final Long count) {
        this.tokens = new HashSet<>(tokens);
        this.rootToken = rootToken;
        this.count = count;
    }

    @Override
    public String getRootToken() {
        return rootToken;
    }

    @Override
    public Set<String> getAllTokens() {
        return tokens;
    }

    @Override
    public Long getCount() {
        return count;
    }

    //TODO Need to override equals and hash

    @Override
    public String toString() {
        return String.format("RootToken: [%s] tokens: [%s]",rootToken ,tokens);
    }

    public class WordPlaceholder implements IWordPlaceholder {

        private final String token;

        public WordPlaceholder(String token) {
            this.token = token;
        }

        @Override
        public IWord getWord() {
            return Word.this;
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WordPlaceholder)) return false;

            WordPlaceholder that = (WordPlaceholder) o;

            if (!token.equals(that.token) || !this.getWord().equals(that.getWord()))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            //TODO Is this good enough.
            int result = 17;
            result = 37 * result + token.hashCode();
            result = 37 * result + this.getWord().hashCode();
            return result;
        }
        
    }
}
