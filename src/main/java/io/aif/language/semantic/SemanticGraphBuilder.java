package io.aif.language.semantic;


import io.aif.associations.builder.AssociationsGraphBuilder;
import io.aif.associations.calculators.vertex.IVertexWeightCalculator;
import io.aif.associations.model.IGraph;
import io.aif.language.semantic.weights.node.word.TokensCountBasedWeightCalculator;
import io.aif.language.word.IWord;

import java.util.*;
import java.util.stream.Collectors;

public class SemanticGraphBuilder {

    private final AssociationsGraphBuilder<IWord> associationsGraphBuilder;

    public SemanticGraphBuilder() {
        associationsGraphBuilder = new AssociationsGraphBuilder<>(generateWeightCalculator());
    }

    public IGraph<IWord> build(final Collection<IWord.IWordPlaceholder> placeholders) {
        final List<IWord> words = placeholders.stream().map(IWord.IWordPlaceholder::getWord).collect(Collectors.toList());
        return associationsGraphBuilder.buildGraph(words);
    }

    private Map<IVertexWeightCalculator<IWord>, Double> generateWeightCalculator() {
        final Map<IVertexWeightCalculator<IWord>, Double> calculators = new HashMap<>();
        calculators.put(new TokensCountBasedWeightCalculator(), .85);
        return calculators;
    }

}
