package io.aif.language.common;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface ISplitter<T1, T2> extends Function<T1, List<T2>> {

  public List<T2> split(final T1 target);

  @Override
  public default List<T2> apply(T1 t1) {
    return split(t1);
  }
}
