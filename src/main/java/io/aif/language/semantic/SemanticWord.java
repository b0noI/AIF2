package io.aif.language.semantic;

import com.google.common.annotations.VisibleForTesting;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.semantic.weights.node.word.IWordWeightCalculator;
import io.aif.language.word.IWord;

import java.util.*;
import java.util.stream.Collectors;

class SemanticWord implements ISemanticNode<IWord> {

    private final INodeWeightCalculator<IWord> weightCalculator;

    private final IWord word;

    private final Map<ISemanticNode<IWord>, Edge> connections;

    public SemanticWord(final IWord word, final INodeWeightCalculator<IWord> weightCalculator) {
        this(word, weightCalculator, new HashMap<>());
    }

    @VisibleForTesting
    SemanticWord(final IWord word,
                           final INodeWeightCalculator<IWord> weightCalculator,
                           final Map<ISemanticNode<IWord>, Edge> connections ) {
        this.word = word;
        this.weightCalculator = weightCalculator;
        this.connections = connections;
    }

    public SemanticWord(final IWord word) {
        this(word, IWordWeightCalculator.createDefaultWeightCalculator(null, null));
    }

    @Override
    public double weight() {
        return weightCalculator.calculateWeight(this.item());
    }

    @Override
    public double connectionWeight(final ISemanticNode<IWord> semanticNode) {
        return (getAverageDistance(semanticNode) / maxConnection());
    }

    @Override
    public Set<ISemanticNode<IWord>> connectedItems() {
        return connections.keySet();
    }

    @Override
    public IWord item() {
        return word;
    }

    public void addEdge(final ISemanticNode<IWord> node, final Edge edge) {
        connections.put(node, edge);
    }

    public void addEdge(final ISemanticNode<IWord> node, final double weight) {
        final Edge e = connections.getOrDefault(node, new Edge());
        e.addDistance(weight);
    }

    @VisibleForTesting
    protected double maxConnection() {
        final OptionalDouble optionalMax = connections.keySet().stream()
                .map(key -> connections.get(key).getDistances())
                .mapToDouble(distances -> distances.stream().collect(Collectors.summarizingDouble(x -> x)).getAverage())
                .max();
        if (optionalMax.isPresent()) return optionalMax.getAsDouble();
        return .0;
    }

    @VisibleForTesting
    protected double getAverageDistance(final ISemanticNode<IWord> node) {
        return connections.get(node)
                .getDistances()
                .stream()
                .collect(Collectors.summarizingDouble(x -> x))
                .getAverage();
    }

    public static class Edge {

        private final List<Double> distances = new ArrayList<>();

        public void addDistance(final Double distance) {
            distances.add(distance);
        }

        public List<Double> getDistances() {
            return this.distances;
        }

    }

}
