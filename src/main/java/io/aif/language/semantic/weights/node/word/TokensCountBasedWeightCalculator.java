package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.word.IWord;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TokensCountBasedWeightCalculator implements IWordWeightCalculator {

    @Override
    public double calculateWeight(final IWord node) {
        final Set<String> mergedTokens = node.getAllTokens().stream().map(String::toLowerCase).collect(Collectors.toSet());
        return 1.0 - 1.0 / (double)mergedTokens.size();
    }

}
