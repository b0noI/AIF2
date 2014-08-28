package com.aif.language.common;

import java.util.List;

public interface IExtractor<T1, T2> {

    public List<T2> extract(final T1 from);

}
