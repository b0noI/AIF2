package io.aif.language.common;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@FunctionalInterface
public interface IMapper<T, S> extends Function<T, S>{

    public S map(T nestedList);

    @Override
    default S apply(T t) {return map(t);}

    default List<S> mapAll(List<T> elements) {
        return elements
                .parallelStream()
                .map(this)
                .collect(Collectors.toList());
    }

}
