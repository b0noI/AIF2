package io.aif.language.word.dict;

import java.util.*;
import java.util.stream.Collectors;
import io.aif.language.common.settings.ISettings;
import io.aif.language.word.comparator.ISetComparator;

public class Grouper implements IGroupable {

    private static final double COMPARATOR_THRESHOLD = ISettings.SETTINGS.WordSetDictComparatorThreshold();

    @Override
    // SetComparator should be named GroupComparator
    public List<Set<String>> group(Collection<String> tokens, ISetComparator groupComparator) {
        List<Set<String>> tokenSets = tokens
                .stream()
                .map(token -> new HashSet<String>() {{
                    add(token);
                }})
                .collect(Collectors.toList());

        final WordSetDict wordSetDict = new WordSetDict(groupComparator);
        tokenSets.stream().forEach(wordSetDict::mergeSet);
        return wordSetDict.getTokens();
    }
}
