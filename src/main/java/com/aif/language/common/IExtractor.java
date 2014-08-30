package com.aif.language.common;

import java.util.Optional;
import java.util.function.Function;

public interface IExtractor<T1, T2> extends Function<T1, Optional<T2>> {

    public Optional<T2> extract(final T1 from);

    @Override
    public default Optional<T2> apply(T1 t1) {
        return extract(t1);
    }

}
