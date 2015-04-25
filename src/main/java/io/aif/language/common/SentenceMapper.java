package io.aif.language.common;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SentenceMapper<T, S> implements IMapper<Collection<T>, List<S>> {

    private final ISearchable<T, S> searchable;

    public SentenceMapper(ISearchable<T, S> searchable) {
        this.searchable = searchable;
    }

    @Override
    public List<S> map(Collection<T> nestedList) {
        return nestedList
                .stream()
                .map(item -> searchable.search(item).get())
                .collect(Collectors.toList());
    }

}
