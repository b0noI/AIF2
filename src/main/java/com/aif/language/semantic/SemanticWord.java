package com.aif.language.semantic;

import com.aif.language.semantic.weights.CompositeWeightCalculator;
import com.aif.language.semantic.weights.IWeightCalculator;
import com.aif.language.semantic.weights.word.ConnectionBasedWeightCalculator;
import com.aif.language.semantic.weights.word.TokensCountBasedWeightCalculator;
import com.aif.language.word.Word;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SemanticWord implements ISemanticNode<Word> {

    private static  final int                                   MAX_DISTANCE_BETWEEN_WORDS  = 5;

    private         final IWeightCalculator<Word>               weightCalculator;

    private         final Word                                  word;

    private         final Map<ISemanticNode<Word>, Connection>  connections                 = new HashMap<>();

    public SemanticWord(final Word word, final IWeightCalculator<Word> weightCalculator) {
        this.word = word;
        this.weightCalculator = weightCalculator;
    }

    public SemanticWord(final Word word) {
        this(word, SemanticWord.createDefaultWeightCalculator());
    }

    @Override
    public double weight() {
        return weightCalculator.calculateWeight(this);
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

    private static IWeightCalculator<Word> createDefaultWeightCalculator() {
        final Set<IWeightCalculator<Word>> calculators = new HashSet<>();

        calculators.add(new TokensCountBasedWeightCalculator());
        calculators.add(new ConnectionBasedWeightCalculator());

        return new CompositeWeightCalculator<>(calculators);
    }

}
