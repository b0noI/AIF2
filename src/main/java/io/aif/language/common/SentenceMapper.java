package io.aif.language.common;

import java.util.List;
import java.util.stream.Collectors;

public class SentenceMapper<T, S> implements IMapper<List<T>,List<S>> {

    private final ISearchable<T, S> searchable;

    public SentenceMapper(ISearchable<T, S> searchable) {
        this.searchable = searchable;
    }

    @Override
    public List<S> map(List<T> rawSentence) {
        return rawSentence
                .parallelStream()
                .map(item -> searchable.search(item).get())
                .collect(Collectors.toList());
    }
}
