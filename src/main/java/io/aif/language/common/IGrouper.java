package io.aif.language.common;

import java.util.*;

public interface IGrouper {

    public List<Set<String>> group(final Collection<String> tokens);
}
