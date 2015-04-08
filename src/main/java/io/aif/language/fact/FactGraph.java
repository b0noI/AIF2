package io.aif.language.fact;

import java.util.*;
import java.util.Optional;

class FactGraph {

    private final Map<IFact, Set<IFact>> g;

    public FactGraph() {
        g = new HashMap<>();
    }

    public void connect(IFact from, IFact to) {
        //TODO Should we deal with null from and to values
        Set<IFact> v = g.getOrDefault(from, new HashSet<IFact>());
        v.add(to);
        g.put(from, v);

        Set<IFact> v2 = g.getOrDefault(to, new HashSet<IFact>());
        v2.add(from);
        g.put(to, v);
    }

    /**
     * Add disconnected single components to the graph.
     *
     * @param fact
     */
    public void add(IFact fact) {
        //TODO Should we deal with null values
        Set<IFact> v = g.getOrDefault(fact, new HashSet<IFact>());
        g.put(fact, v);
    }

    public Optional<Set<IFact>> getNeighbours(IFact v) {
        if (g.containsKey(v))
            return Optional.of(g.get(v));
        else
            return Optional.empty();
    }

    public Optional<Set<IFact>> getAll() {
        if (g.keySet().size() > 0)
            return Optional.of(g.keySet());
        else
            return Optional.empty();
    }
}
