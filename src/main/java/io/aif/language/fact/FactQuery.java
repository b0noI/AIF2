package io.aif.language.fact;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.word.IWord;

import java.util.*;
import java.util.stream.Collectors;

class FactQuery {

    private final FactGraph<IFact> graph;
    private final Set<IFact> facts;
    private final DFS<IFact> traverser;

    public FactQuery(final FactGraph g) {
        this.graph = g;
        facts = graph.getAll().get();
        traverser = new DFS<>(graph);
    }

    public Optional<List<List<IFact>>> findPath(final ISemanticNode<IWord> properNoun1,
                                                final ISemanticNode<IWord> properNoun2) {
        assert (properNoun1.isProperNoun().isTrue()
                && properNoun2.isProperNoun().isTrue());

        Set<IFact> factsContainingProperNoun1 = getFactsWithProperNoun(properNoun1);
        Set<IFact> factsContainingProperNoun2 = getFactsWithProperNoun(properNoun2);

        List<IFact> interception = factsContainingProperNoun1
                .parallelStream()
                .filter(fact -> factsContainingProperNoun2.contains(fact))
                .collect(Collectors.toList());

        if (interception.size() > 0)
            return Optional.of(
                    new ArrayList<List<IFact>>() {{
                        add(interception);
                    }});

        List<List<IFact>> paths = new ArrayList<>();
        for (IFact sfOut : factsContainingProperNoun1) {
            for (IFact sfIn : factsContainingProperNoun2) {
                List<List<IFact>> r = traverser.findPath(sfOut, sfIn);
                if (r.size() > 0)
                    paths.addAll(r);
            }
        }

        return (paths.size() > 0) ?
                Optional.of(getCheapestPath(paths)) :
                Optional.empty();
    }

    private Set<IFact> getFactsWithProperNoun(ISemanticNode<IWord> properNoun1) {
        return facts
                .stream()
                .filter(fact -> fact.hasProperNoun(properNoun1))
                .collect(Collectors.toSet());
    }

    private List<List<IFact>> getCheapestPath(final List<List<IFact>> paths) {
        OptionalInt minPathValue = paths.stream().mapToInt(List::size).min();
        return paths
                .stream()
                .filter(path -> path.size() == minPathValue.getAsInt())
                .collect(Collectors.toList());
    }
}
