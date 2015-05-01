package io.aif.language.fact;

import java.util.*;
import java.util.stream.Collectors;

public class DFS <T> {

    final private FactGraph<T> graph;

    public DFS(FactGraph<T> graph) {
        this.graph = graph;
    }

    public List<List<T>> findPath(T source, T dest) {
        List<T> initialPath = new ArrayList<>();
        initialPath.add(source);
        return getPath(initialPath, dest);
    }

    private List<List<T>> getPath(List<T> path, T dest) {
        List<List<T>> result = new ArrayList<>();

        T last = path.get(path.size() - 1);
        if (last == dest) {
            result.add(path);
            return result;
        }

        Optional<Set<T>> neighbours = graph.getNeighbours(last);
        if (neighbours.isPresent() == false)
            return result;

        return neighbours.get().stream().filter(neighbour -> !path.contains(neighbour)).parallel().map(neighbour -> {
            List<T> pathCopy = new ArrayList<T>(path);
            pathCopy.add(neighbour);
            return getPath(pathCopy, dest);
        }).flatMap(List::stream).collect(Collectors.toList());
    }

    private List<List<T>> getCheapestPath(final List<List<T>> paths) {
        OptionalInt minPathValue = paths.stream().mapToInt(List::size).min();
        return paths
                .stream()
                .filter(path -> path.size() == minPathValue.getAsInt())
                .collect(Collectors.toList());
    }
}
