package com.aif.language.semantic;

import com.aif.language.word.Word;

import java.util.List;

public class SemanticWord implements ISemanticNode<Word>{

    private final Word word;

    public SemanticWord(final Word word) {
        this.word = word;
    }

    @Override
    public double weight() {
        return 0;
    }

    @Override
    public double connectionWeight(final Word semanticNode) {
        return 0;
    }

    @Override
    public List<Word> connectedItems() {
        return null;
    }

}
