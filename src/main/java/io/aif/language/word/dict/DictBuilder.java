package io.aif.language.word.dict;

import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IDict;
import io.aif.language.word.comparator.ISetComparator;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;


class DictBuilder implements IDictBuilder<Collection<String>> {

    private final ISetComparator comparator;

    private final RootTokenExtractor rootTokenExtractor;

    public DictBuilder(final ISetComparator comparator, final ITokenComparator tokenComparator) {
        this.comparator = comparator;
        this.rootTokenExtractor = new RootTokenExtractor(tokenComparator);
    }

    public DictBuilder(final ITokenComparator tokenComparator) {
        this(ISetComparator.createDefaultInstance(tokenComparator), tokenComparator);
    }

    public DictBuilder() {
        this(ITokenComparator.defaultComparator());
    }

    @Override
    public IDict build(final Collection<String> from) {
        final List<Set<String>> tokenSets = from
            .stream()
            .map(token -> new HashSet<String>() {{
                add(token);
            }})
            .collect(Collectors.toList());

        final WordSetDict wordSetDict = new WordSetDict(comparator);
        tokenSets.stream().forEach(wordSetDict::mergeSet);
        return new Dict(
                wordSetDict.getTokens()
                .parallelStream()
                .filter(set -> !set.isEmpty())
                .map(set -> {
                    final Optional<String> rootTokenOpt = rootTokenExtractor.extract(set);
                    final String rootToken;
                    if (rootTokenOpt.isPresent()) {
                        rootToken = rootTokenOpt.get();
                    } else {
                        rootToken = set.iterator().next();
                    }
                    return new Word(rootToken, set, wordSetDict.getCount(set));
                })
                .collect(Collectors.toSet()));
    }

}
