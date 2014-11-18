package io.aif.language.word.dict;


import java.util.List;
import java.util.Map;
import java.util.Set;

class SetDict {

    private Map<String, Set<String>> tokensSetCache;

    private List<Set<String>> tokens;

    public void addTokenToSet(final String token, final Set<String> set) {
        if (!tokens.contains(set)) {
            tokens.add(set);
        }
        set.add(token);
        tokensSetCache.put(token, set);
    }

    public boolean tokenHaveSet(final String token) {
        return tokensSetCache.keySet().contains(token);
    }

    public Set<String> getTokenSet(final String token) {
        return tokensSetCache.get(token);
    }

    public void addSetToSet(final Set<String> set1, final Set<String> set2) {
        set2.forEach(token -> {
            tokensSetCache.put(token, set1);
        });

    }

}
