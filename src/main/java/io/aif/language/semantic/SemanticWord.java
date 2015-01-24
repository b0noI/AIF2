package io.aif.language.semantic;

import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.semantic.weights.node.word.IWordWeightCalculator;
import io.aif.language.word.IWord;

import java.util.*;
import java.util.stream.Collectors;

class SemanticWord implements ISemanticNode<IWord> {

    private static final int MAX_DISTANCE_BETWEEN_WORDS = 5;

    private final INodeWeightCalculator<IWord> weightCalculator;

    private final IWord word;

    private final Map<ISemanticNode<IWord>, Edge> connections = new HashMap<>();

    public SemanticWord(final IWord word, final INodeWeightCalculator<IWord> weightCalculator) {
        this.word = word;
        this.weightCalculator = weightCalculator;
    }

    public SemanticWord(final IWord word) {
        this(word, IWordWeightCalculator.createDefaultWeightCalculator());
    }

    @Override
    public double weight() {
        return weightCalculator.calculateWeight(this);
    }

    @Override
    public double connectionWeight(final ISemanticNode<IWord> semanticNode) {
        return (connections.get(semanticNode).getDistances().stream()
                .collect(Collectors.summarizingDouble(x -> x))
                .getAverage() / MAX_DISTANCE_BETWEEN_WORDS) * semanticNode.weight();

    }

    @Override
    public Set<ISemanticNode<IWord>> connectedItems() {
        return connections.keySet();
    }

    @Override
    public IWord item() {
        return word;
    }

    public void addConnection(final ISemanticNode<IWord> node, final Edge edge) {
        connections.put(node, edge);
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
