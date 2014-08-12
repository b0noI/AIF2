package com.aif.language.semantic;

import com.aif.language.word.Word;

import java.util.*;
import java.util.stream.Collectors;

public class SemanticWord implements ISemanticNode<Word> {

    private static  final int                                   MAX_DISTANCE_BETWEEN_WORDS  = 5;

    private static  final int                                   MAX_WORD_CONNECTION_COUNT   = 20_000;

    private         final Word                                  word;

    private         final Map<ISemanticNode<Word>, Connection>  connections                 = new HashMap<>();

    public SemanticWord(final Word word) {
        this.word = word;
    }

    @Override
    public double weight() {
        final Set<ISemanticNode<Word>> items = this.connectedItems();

        final OptionalDouble maxConnectionWeightOptional = items
                .parallelStream()
                .mapToDouble(word -> connectionWeight(word))
                .max();

        if (!maxConnectionWeightOptional.isPresent())
            return 0;

        final double maxConnectionWeight = maxConnectionWeightOptional.getAsDouble();
        final double normalizedConnectionCount = items.size() / MAX_WORD_CONNECTION_COUNT;

        return maxConnectionWeight * (1. - normalizedConnectionCount);
    }

    @Override
    public double connectionWeight(final ISemanticNode<Word> semanticNode) {

        return (connections.get(semanticNode).getDistances().stream()
                .collect(Collectors.summarizingDouble(x -> x))
                .getAverage() / MAX_DISTANCE_BETWEEN_WORDS) * semanticNode.weight();

    }

    @Override
    public Set<ISemanticNode<Word>> connectedItems() {
        return connections.keySet();
    }

    @Override
    public Word item() {
        return word;
    }

    public static class Connection {

        private final List<Double> distances = new ArrayList<>();

        public void addDistance(final Double distance) {
            distances.add(distance);
        }

        public List<Double> getDistances() {
            return this.distances;
        }

    }

}
