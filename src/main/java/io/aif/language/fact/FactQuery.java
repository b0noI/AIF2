package io.aif.language.fact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

import io.aif.graph.DFS;
import io.aif.graph.simple.ISimpleGraph;
import io.aif.language.word.IWord;

class FactQuery implements IFactQuery {

  private final ISimpleGraph<IFact> graph;
  private final Set<IFact> facts;
  private final DFS<IFact> traverser;

  public FactQuery(final ISimpleGraph<IFact> g) {
    this.graph = g;
    facts = graph.getVertices();
    traverser = new DFS<>(graph);
  }

  public List<List<IFact>> findPath(final IWord properNoun1,
                                    final IWord properNoun2) {

    final Set<IFact> factsContainingProperNoun1 = getFactsWithProperNoun(properNoun1);
    final Set<IFact> factsContainingProperNoun2 = getFactsWithProperNoun(properNoun2);

    final List<IFact> interception = factsContainingProperNoun1
        .parallelStream()
        .filter(factsContainingProperNoun2::contains)
        .collect(Collectors.toList());

    if (!interception.isEmpty()) return Collections.singletonList(interception);

    final List<List<IFact>> paths = new ArrayList<>();
    for (IFact sfOut : factsContainingProperNoun1) {
      for (IFact sfIn : factsContainingProperNoun2) {
        final List<List<IFact>> r = traverser.findPath(sfOut, sfIn);
        if (!r.isEmpty()) paths.addAll(r);
      }
    }

    return getCheapestPath(paths);
  }

  @Override
  public Set<IFact> allFacts() {
    return graph.getVertices();
  }

  private Set<IFact> getFactsWithProperNoun(IWord properNoun1) {
    return facts
        .stream()
        .filter(fact -> fact.hasNamedEntity(properNoun1))
        .collect(Collectors.toSet());
  }

  private List<List<IFact>> getCheapestPath(final List<List<IFact>> paths) {
    final OptionalInt minPathValue = paths.stream().mapToInt(List::size).min();

    if (!minPathValue.isPresent()) return paths;

    return paths
        .stream()
        .filter(path -> path.size() == minPathValue.getAsInt())
        .collect(Collectors.toList());
  }
}
