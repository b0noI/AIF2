package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.word.IWord;

public class TokensCountBasedWeightCalculator implements IWordWeightCalculator {

    @Override
    public double calculateWeight(final ISemanticNode<IWord> semanticNode) {
        final IWord word = semanticNode.item();
        return 1.0 - 1.0 / (double)word.getAllTokens().size();
    }

}
