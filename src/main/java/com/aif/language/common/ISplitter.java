package com.aif.language.common;

import java.util.List;

/**
 * Created by vsk on 8/28/14.
 */
public interface ISplitter<T> {

    public List<T> split(T target);

}
