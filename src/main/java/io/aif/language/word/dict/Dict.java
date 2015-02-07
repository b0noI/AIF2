package io.aif.language.word.dict;

import io.aif.language.common.IDict;
import io.aif.language.common.ISearchable;
import io.aif.language.word.IWord;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


class Dict implements IDict<IWord>, ISearchable<String, IWord> {

    private final Set<IWord> words;
    
    private final Map<String, IWord> reverseIndex;

    private Dict(final Set<IWord> words, Map<String, IWord> reverseIndex) {
        this.words = words;
        this.reverseIndex = reverseIndex;
    }

    @Override
    public Set<IWord> getWords() {
        return words;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        getWords()
                .stream()
                .sorted((w1, w2) -> w1.getRootToken().compareTo(w2.getRootToken()))
                .forEach(word -> stringBuilder.append(String.format("%s\n", word.toString())));
        return stringBuilder.toString();
    }

    @Override
    public Optional<IWord> search(String token) {
        return reverseIndex.containsKey(token)
                ? Optional.of(reverseIndex.get(token))
                : Optional.empty();
    }

    private static Map<String, IWord> generateReverseIndex(Set<IWord> words) {
        //TODO: Opportunities for parallelization?
        Map<String, IWord> reverseIndex = new HashMap<>();
        for (IWord word : words)
            word.getAllTokens().stream().forEach(token -> reverseIndex.put(token, word));
        return reverseIndex;
    }

    public static Dict create(Set<IWord> words) {
        Dict d = new Dict(words, generateReverseIndex(words));
        return d;
    }
    
}
