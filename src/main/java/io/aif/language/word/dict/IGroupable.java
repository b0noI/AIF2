package io.aif.language.word.dict;

import io.aif.language.word.comparator.ISetComparator;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IGroupable {

    public List<Set<String>> group(Collection<String> tokens, ISetComparator comparator);
}
