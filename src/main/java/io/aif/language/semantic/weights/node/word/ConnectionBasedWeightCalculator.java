package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.semantic.weights.edge.IEdgeWeightCalculator;
import io.aif.language.word.IWord;

import java.util.OptionalDouble;
import java.util.Set;

public class ConnectionBasedWeightCalculator implements IWordWeightCalculator {
    
    // public zone

    public ConnectionBasedWeightCalculator(IEdgeWeightCalculator<IWord> edgeWeightCalculator) {
        this.edgeWeightCalculator = edgeWeightCalculator;
    }

    @Override
    public double calculateWeight(final IWord semanticNode) {
        final Set<ISemanticNode<IWord>> items = semanticNode.connectedItems();

        final OptionalDouble maxConnectionWeightOptional = items
                .parallelStream()
                .mapToDouble(word -> semanticNode.connectionWeight(word))
                .max();

        if (!maxConnectionWeightOptional.isPresent())
            return 0;

        final double maxConnectionWeight = maxConnectionWeightOptional.getAsDouble();
        final double normalizedConnectionCount = (double)items.size() / (double)MAX_WORD_CONNECTIONS_COUNT;

        return maxConnectionWeight * (1. - normalizedConnectionCount);
    }
    
    // private zone

    private final IEdgeWeightCalculator<IWord> edgeWeightCalculator;

    private static  final int MAX_WORD_CONNECTIONS_COUNT = 20_000;

}
