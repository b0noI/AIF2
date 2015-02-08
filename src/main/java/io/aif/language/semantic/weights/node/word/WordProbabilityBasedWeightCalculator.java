package io.aif.language.semantic.weights.node.word;

import io.aif.language.word.IWord;

import java.util.Set;

/**
 * Created by b0noI on 07/02/2015.
 */
public class WordProbabilityBasedWeightCalculator implements IWordWeightCalculator {
    
    private static final double TARGET = .7;
    
    private final double maxCount;

    public WordProbabilityBasedWeightCalculator(final Set<IWord> words) {
        maxCount = words.stream().mapToDouble(IWord::getCount).max().getAsDouble();
    }

    @Override
    public double calculateWeight(final IWord node) {
        return 1. - Math.abs(TARGET - node.getCount() / maxCount);
    }
    
}
