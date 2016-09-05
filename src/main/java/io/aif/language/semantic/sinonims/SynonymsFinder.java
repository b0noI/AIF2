package io.aif.language.semantic.sinonims;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.aif.algorithms.graph.fuzzy.similarity.GraphNodesSimilarityCalculator;
import io.aif.algorithms.graph.fuzzy.similarity.ISimilarityCalculator;
import io.aif.associations.builder.AssociationGraph;
import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.language.word.IWord;
import javaslang.Tuple2;

public class SynonymsFinder {

  public Set<Tuple2<IWord, IWord>> find(final AssociationGraph<IWord> text) {
    final ISimilarityCalculator<IWord> similarityCalculator =
        new GraphNodesSimilarityCalculator<>(text, .75, .35);

    final List<Tuple2<Tuple2<IWord, IWord>, FuzzyBoolean>> results = text
        .getVertices()
        .stream()
        .flatMap(v -> text.getNeighbors(v).stream().map(n -> new Tuple2<>(v, n)))
        .distinct()
        .filter(this::filter)
        .map(t -> new Tuple2<>(t, similarityCalculator.similar(t)))
        .sorted((tl, tr) ->
            Double.compare(tr._2.getValue(), tl._2.getValue()))
        .collect(Collectors.toList());
    return results.stream()
        .filter(t -> t._2.isTrue())
        .map(t -> t._1)
        .collect(Collectors.toSet());
  }

  private boolean filter(final IWord left, final IWord right) {
    if (left.equals(right)) {
      return false;
    }
    final double diff =
        1. - Math.min(left.getCount(), right.getCount())
            / Math.max(left.getCount(), right.getCount());
    if (left.getCount() < 5 || right.getCount() < 5) {
      return false;
    }
    return true;
  }

  private boolean filter(final Tuple2<IWord, IWord> t) {
    return filter(t._1, t._2);
  }

}
