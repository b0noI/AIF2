package io.aif.language.semantic;

import io.aif.language.word.IWord;

import java.util.Set;


public class SemanticDict implements ISemanticDict {

    private final Set<ISemanticNode<IWord>> semanticWords;

    public SemanticDict(final Set<ISemanticNode<IWord>> semanticWords) {
        this.semanticWords = semanticWords;
    }

    @Override
    public Set<ISemanticNode<IWord>> getWords() {
        return semanticWords;
    }

}
