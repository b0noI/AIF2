package io.aif.language.common;

public interface IDictBuilder<T, R> {

  public IDict<R> build(final T tokens);

}
