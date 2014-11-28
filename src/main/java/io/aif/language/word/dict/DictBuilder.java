package io.aif.language.word.dict;

import io.aif.language.common.ForkJoinPoolLoggerThread;
import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IDict;
import io.aif.language.word.comparator.ISetComparator;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class DictBuilder implements IDictBuilder<Collection<String>> {

    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    private final ISetComparator comparator;

    private final RootTokenExtractor rootTokenExtractor;

    public DictBuilder(final ISetComparator comparator, final ITokenComparator tokenComparator) {
        this.comparator = comparator;
        this.rootTokenExtractor = new RootTokenExtractor(tokenComparator);
    }

    @Override
    public IDict build(final Collection<String> from) {
        final List<Set<String>> tokenSets = from
            .stream()
            .map(token -> new HashSet<String>() {{
                add(token);
            }})
            .collect(Collectors.toList());

        final SetDict setDict = new SetDict(comparator);
        tokenSets.parallelStream().forEach(setDict::mergeSet);
//        final SetDict setDict;
//        try {
//            ForkJoinPoolLoggerThread logger = new ForkJoinPoolLoggerThread(FORK_JOIN_POOL);
//            logger.start();
//            setDict = FORK_JOIN_POOL.submit(new RecursiveSetMerger(tokenSets, 0 ,tokenSets.size(), comparator)).get();
//            logger.stopThread();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
        return new Dict(
                setDict.getTokens()
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
                    return new Word(rootToken, set, setDict.getCount(set));
                })
                .collect(Collectors.toSet()));
    }

    static class RecursiveSetMerger extends RecursiveTask<SetDict> {

        private final List<Set<String>> set;

        private final int from;

        private final int to;

        private final ISetComparator setComparator;

        RecursiveSetMerger(final List<Set<String>> set,
                           final int from,
                           final int to,
                           final ISetComparator setComparator) {
            this.set = set;
            this.from = from;
            this.to = to;
            this.setComparator = setComparator;
        }

        @Override
        protected SetDict compute() {
            if (to - from < 500) {
                final SetDict setDict = new SetDict(setComparator);
                IntStream.range(from, to).forEach(i -> setDict.mergeSet(set.get(i)));
                return setDict;
            }
            final int mid = (to + from) / 2;
            final RecursiveSetMerger leftTask = new RecursiveSetMerger(set, from, mid, setComparator);
            final RecursiveSetMerger rightTask = new RecursiveSetMerger(set, mid, to, setComparator);

            leftTask.fork();
            rightTask.fork();
            final SetDict setDict = leftTask.join();
            setDict.mergeSet(rightTask.join());
            return setDict;
        }

    }

}
