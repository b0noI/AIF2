package io.aif.language.semantic.weights.node.word;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.aif.associations.calculators.vertex.IVertexWeightCalculator;
import io.aif.language.word.IWord;

public class TokensCountBasedWeightCalculator implements IVertexWeightCalculator<IWord> {

  @Override
  public Map<IWord, Double> calculate(
      final Map<IWord, Map<IWord, Double>> vertex, final Map<IWord, Long> count) {

    return vertex.keySet()
        .stream().collect(Collectors.toMap(node -> node, node -> calculate(node)));
  }

  private Double calculate(final IWord node) {
    final Set<String> mergedTokens =
        node.getAllTokens().stream().map(String::toLowerCase).collect(Collectors.toSet());
    return 1.0 - 1.0 / (double) mergedTokens.size();
  }

}
