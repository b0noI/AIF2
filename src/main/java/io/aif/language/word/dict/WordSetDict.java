package io.aif.language.word.dict;


import io.aif.language.common.settings.ISettings;
import io.aif.language.word.comparator.ISetComparator;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

class WordSetDict {

    private static final Logger LOGGER = Logger.getLogger(WordSetDict.class);

    private static final double COMPARATOR_THRESHOLD = ISettings.SETTINGS.WordSetDictComparatorThreshold();

    private final ISetComparator setComparator;

    private Map<String, Set<String>> tokensSetCache = new HashMap<>();

    private List<Set<String>> tokens = new ArrayList<>();

    private Map<String, AtomicLong> tokensCount = new HashMap<>();

    WordSetDict(ISetComparator setComparator) {
        this.setComparator = setComparator;
    }

    public void mergeSet(final Set<String> set) {
        set.forEach(token -> {
            tokensCount.putIfAbsent(token, new AtomicLong());
            tokensCount.get(token).incrementAndGet();
        });

        final int tokensSize = tokens.size();
        final Optional<Set<String>> tokensSetOpt = set.stream().filter(token -> getSet(token).isPresent()).map(token -> getSet(token).get()).findFirst();
        if (tokensSetOpt.isPresent()) {
            final Set<String> tokensSet = tokensSetOpt.get();
            tokensSet.addAll(set);
            set.forEach(token -> tokensSetCache.put(token, tokensSet));
            return;
        }
        for (int i = 0; i < tokens.size(); i++) {
            final Set<String> targetSet = tokens.get(i);
            if (setComparator.compare(targetSet, set) > COMPARATOR_THRESHOLD) {
                targetSet.addAll(set);
                set.forEach(token -> tokensSetCache.put(token, targetSet));
                return;
            }
        }
        if (tokens.size() != tokensSize) {
            mergeSet(set);
        } else {
            tokens.add(set);
            set.forEach(token -> tokensSetCache.put(token, set));
        }
        LOGGER.debug(String.format("words count is: %d", tokens.size()));
    }

    public List<Set<String>> getTokens() {
        return tokens;
    }

    public Long getCount(final Set<String> set) {
        return set.stream().mapToLong(token -> tokensCount.get(token).get()).sum();
    }

    private Optional<Set<String>> getSet(final String token) {
        if (!tokensSetCache.keySet().contains(token)) return Optional.empty();
        return Optional.of(tokensSetCache.get(token));
    }

}
