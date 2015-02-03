package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.word.IWord;

public class TokensCountBasedWeightCalculator implements IWordWeightCalculator {

    @Override
    public double calculateWeight(final IWord node) {
        return 1.0 - 1.0 / (double)node.getAllTokens().size();
    }

}
