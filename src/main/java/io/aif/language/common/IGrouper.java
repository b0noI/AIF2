package io.aif.language.common;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IGrouper {

  public List<Set<String>> group(final Collection<String> tokens);
}
