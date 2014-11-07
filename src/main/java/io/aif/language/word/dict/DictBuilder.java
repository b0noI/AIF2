package io.aif.language.word.dict;

import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.AbstractWord;
import io.aif.language.word.IDict;
import io.aif.language.word.IWord;
import io.aif.language.word.Word;

import java.util.*;
import java.util.stream.Collectors;


class DictBuilder implements IDictBuilder<Collection<String>> {

    private Comparator<Set<String>> comparator;

    public DictBuilder() {
        this(ITokenComparator.defaultComparator());
    }

    public DictBuilder(final ITokenComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public IDict build(final Collection<String> from) {
        final List<Set<String>> words = from
            .stream()
            .map(token -> new HashSet<String>() {{
                add(token);
            }})
            .collect(Collectors.toList());

    }

    private Set<Set<String>>

}
