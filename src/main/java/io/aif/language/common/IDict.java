package io.aif.language.common;


import java.util.Iterator;
import java.util.Set;

public interface IDict<T> extends Iterable<T> {

  public Set<T> getWords();

  @Override
  default public Iterator<T> iterator() {
    return getWords().iterator();
  }

}
