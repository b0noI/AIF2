package io.aif.language.word;

import java.util.Set;

public interface IWord {

    public String getRootToken();

    public Set<String> getAllTokens();

    public static interface IWordPlaceholder {

        public IWord getWord();

        public String getToken();

    }

}
