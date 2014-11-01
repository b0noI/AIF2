package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.word.Word;

public class TokensCountBasedWeightCalculator implements IWordWeightCalculator {

    @Override
    public double calculateWeight(ISemanticNode<Word> semanticNode) {
        final Word word = semanticNode.item();
        return 1.0 - 1.0 / (double)word.getAllTokens().size();
    }

}
