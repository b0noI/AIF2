package com.aif.language.semantic.weights.node.word;

import com.aif.language.semantic.ISemanticNode;
import com.aif.language.word.Word;

import java.util.OptionalDouble;
import java.util.Set;

public class ConnectionBasedWeightCalculator implements IWordWeightCalculator {

    private static  final int MAX_WORD_CONNECTION_COUNT = 20_000;

    @Override
    public double calculateWeight(ISemanticNode<Word> semanticNode) {
        final Set<ISemanticNode<Word>> items = semanticNode.connectedItems();

        final OptionalDouble maxConnectionWeightOptional = items
                .parallelStream()
                .mapToDouble(word -> semanticNode.connectionWeight(word))
                .max();

        if (!maxConnectionWeightOptional.isPresent())
            return 0;

        final double maxConnectionWeight = maxConnectionWeightOptional.getAsDouble();
        final double normalizedConnectionCount = items.size() / MAX_WORD_CONNECTION_COUNT;

        return maxConnectionWeight * (1. - normalizedConnectionCount);
    }

}
