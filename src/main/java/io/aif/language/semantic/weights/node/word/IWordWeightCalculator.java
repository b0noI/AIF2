package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.word.Word;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vsk on 8/19/14.
 */
public interface IWordWeightCalculator extends INodeWeightCalculator<Word> {

    public static INodeWeightCalculator<Word> createDefaultWeightCalculator() {
        final Set<INodeWeightCalculator<Word>> calculators = new HashSet<>();

        calculators.add(new TokensCountBasedWeightCalculator());
        calculators.add(new ConnectionBasedWeightCalculator());

        return new CompositeNodeWeightCalculator<>(calculators);
    }

}
