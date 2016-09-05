package io.aif.language.common;

import java.util.Optional;

public interface ISearchable<T, R> {

  public Optional<R> search(T subject);

}
