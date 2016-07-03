package io.aif.language.word;

import java.util.Set;

public interface IWord {

    public String getRootToken();

    public Set<String> getAllTokens();

    public Long getCount();

    public int getTokenCount(String token);

    public static interface IWordPlaceholder {

        public IWord getWord();

        public String getToken();

    }

}
