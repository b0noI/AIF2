package io.aif.language.fact;

import java.util.*;
import java.util.Optional;

class FactGraph<T> {

    private final Map<T, Set<T>> g;

    public FactGraph() {
        g = new HashMap<>();
    }

    public void connect(T from, T to) {
        //TODO Should we deal with null from and to values
        Set<T> v = g.getOrDefault(from, new HashSet<T>());
        v.add(to);
        g.put(from, v);

        Set<T> v2 = g.getOrDefault(to, new HashSet<T>());
        v2.add(from);
        g.put(to, v2);
    }

    /**
     * Add disconnected single components to the graph.
     *
     * @param fact
     */
    public void add(T fact) {
        //TODO Should we deal with null values
        Set<T> v = g.getOrDefault(fact, new HashSet<T>());
        g.put(fact, v);
    }

    public Optional<Set<T>> getNeighbours(T v) {
        if (g.containsKey(v) && g.get(v).size() > 0)
            return Optional.of(g.get(v));
        else
            return Optional.empty();
    }

    public Optional<Set<T>> getAll() {
        if (g.keySet().size() > 0)
            return Optional.of(g.keySet());
        else
            return Optional.empty();
    }
}
