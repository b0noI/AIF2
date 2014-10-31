package com.aif.language.semantic.weights.node.word;

import com.aif.language.semantic.ISemanticNode;
import com.aif.language.word.Word;

public class TokensCountBasedWeightCalculator implements IWordWeightCalculator {

    @Override
    public double calculateWeight(ISemanticNode<Word> semanticNode) {
        final Word word = semanticNode.item();
        return 1.0 - 1.0 / (double)word.getAllTokens().size();
    }

}
