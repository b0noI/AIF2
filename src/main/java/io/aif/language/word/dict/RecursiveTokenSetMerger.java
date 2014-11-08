package io.aif.language.word.dict;

import io.aif.language.word.comparator.ISetComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by b0noI on 08/11/14.
 */
class RecursiveTokenSetMerger extends RecursiveTask<List<Set<String>>> {

    private final ISetComparator comparator;

    private final List<Set<String>> tokenSets;

    public RecursiveTokenSetMerger(final ISetComparator comparator, final List<Set<String>> tokenSets) {
        this.comparator = comparator;
        this.tokenSets = tokenSets;
    }

    @Override
    protected List<Set<String>> compute() {
        if (tokenSets.size() <= 100) {
            return merge(tokenSets);
        }

        final int middle = tokenSets.size() / 2;
        final List<Set<String>> left = tokenSets.subList(0, middle);
        final List<Set<String>> right = tokenSets.subList(middle, tokenSets.size());

        final RecursiveTokenSetMerger leftTask = new RecursiveTokenSetMerger(comparator, left);
        final RecursiveTokenSetMerger rightTask = new RecursiveTokenSetMerger(comparator, right);

        leftTask.fork();
        rightTask.fork();

        final List<Set<String>> leftResult = leftTask.join();
        final List<Set<String>> rightResult = rightTask.join();
        return merge(leftResult, rightResult);
    }

    private List<Set<String>> merge(final List<Set<String>> leftSet, final List<Set<String>> rightSet) {
        final List<Set<String>> tmpLeftSet = new ArrayList<>(leftSet);
        final List<Set<String>> tmpRightSet = new ArrayList<>(rightSet);
        for (int i = 0; i < tmpLeftSet.size(); i++) {
            final Set<String> left = tmpLeftSet.get(i);
            for (int j = 0; j < tmpRightSet.size(); j++) {
                final Set<String> right = tmpRightSet.get(j);
                if (comparator.compare(left, right) > .85) {
                    left.addAll(right);
                    tmpRightSet.remove(j);
                    j--;
                }
            }
        }
        tmpLeftSet.addAll(tmpRightSet);
        return tmpLeftSet;
    }

    private List<Set<String>> merge(final List<Set<String>> tokenSets) {
        final List<Set<String>> tmpSet = new ArrayList<>(tokenSets);
        for (int i = 0; i < tmpSet.size(); i++) {
            final Set<String> left = tmpSet.get(i);
            for (int j = i + 1; j < tmpSet.size(); j++) {
                final Set<String> right = tmpSet.get(j);
                if (comparator.compare(left, right) > .75) {
                    left.addAll(right);
                    tmpSet.remove(j);
                    j--;
                }
            }
        }
        return tmpSet;
    }

}
