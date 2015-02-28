package io.aif.language.semantic.weights.node;


import io.aif.language.semantic.ISemanticNode;
import io.aif.language.semantic.weights.node.word.CompositeNodeWeightCalculator;
import io.aif.language.semantic.weights.node.word.ConnectionBasedWeightCalculator;
import io.aif.language.semantic.weights.node.word.TokensCountBasedWeightCalculator;
import io.aif.language.word.IWord;

import java.util.HashSet;
import java.util.Set;

public interface INodeWeightCalculator<T> {

    public double calculateWeight(final T node);

}
