package com.aif.language.semantic;

import com.aif.language.word.Word;

import java.util.*;

public class SemanticWord implements ISemanticNode<Word>{

    private final Word word;

    private final Map<Word, Double> connections = new HashMap<>();

    public SemanticWord(final Word word) {
        this.word = word;
    }

    @Override
    public double weight() {
        final OptionalLong maxCount = word.getTokens().stream()
                .mapToLong(token -> word.tokenCount(token))
                .max();

        if (!maxCount.isPresent())
            return 0.0;

        // TODO
        return 0.0;
    }

    @Override
    public double connectionWeight(final Word semanticNode) {
        return connections.get(semanticNode);
    }

    @Override
    public Set<Word> connectedItems() {
        return connections.keySet();
    }

    @Override
    public Word getNode() {
        return null;
    }

}
