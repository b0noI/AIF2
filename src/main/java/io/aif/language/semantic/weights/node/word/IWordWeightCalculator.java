package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.word.IWord;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by b0noI on 01/02/2015.
 */
public interface IWordWeightCalculator extends INodeWeightCalculator<IWord> {

    public static INodeWeightCalculator<IWord> createDefaultWeightCalculator(final IEdgeWeightCalculator<IWord> edgeWeightCalculator,
                                                                             final Map<IWord, Map<IWord, List<Double>>> distancesGraph) {
        final Set<INodeWeightCalculator<IWord>> calculators = new HashSet<>();

        calculators.add(new TokensCountBasedWeightCalculator());
        calculators.add(new WordProbabilityBasedWeightCalculator(distancesGraph.keySet()));
        calculators.add(new ConnectionBasedWeightCalculator(edgeWeightCalculator, distancesGraph));

        return new CompositeNodeWeightCalculator<>(calculators);
    }
    
}
