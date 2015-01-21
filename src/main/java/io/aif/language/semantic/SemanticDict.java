package io.aif.language.semantic;

import io.aif.language.word.IWord;

import java.util.Set;

/**
 * Created by vsk on 1/20/15.
 */
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
