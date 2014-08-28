package com.aif.language.common;

import java.util.List;

/**
 * Created by vsk on 8/28/14.
 */
public interface ISplitter<T1, T2> {

    public List<T2> split(T1 target);

}
