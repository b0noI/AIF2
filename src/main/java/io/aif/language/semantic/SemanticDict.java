package io.aif.language.semantic;

import io.aif.language.common.ISearchable;
import io.aif.language.word.IWord;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * TODO: Implementing ISearchable on Dict classes violate the open closed principle (open for extension closed
 * for modification). A better method would be to create an abstraction which can take in a Dict Object
 * and make it searchable. A tricky part is the creation of reverse index as it would vary from dict to dict but
 * a lambda should help us there.
 *
 * Further this adds testing overhead
 */

class SemanticDict implements ISemanticDict, ISearchable<String, ISemanticNode> {

    private final Set<ISemanticNode<IWord>> semanticWords;

    private final Map<String, ISemanticNode<IWord>> reverseIndex;

    private SemanticDict(final Set<ISemanticNode<IWord>> semanticWords,
                         final Map<String, ISemanticNode<IWord>> reverseIndex) {
        this.semanticWords = semanticWords;
        this.reverseIndex = reverseIndex;
    }

    @Override
    public Set<ISemanticNode<IWord>> getWords() {
        return semanticWords;
    }

    @Override
    public Optional<ISemanticNode> search(String subject) {
        return reverseIndex.containsKey(subject)
                ? Optional.of(reverseIndex.get(subject))
                : Optional.empty();
    }

    private static Map<String, ISemanticNode<IWord>> generateReverseIndex(Set<ISemanticNode<IWord>> semanticNodes) {
        //TODO: Opportunities for parallelization?
        Map<String, ISemanticNode<IWord>> reverseIndex = new HashMap<>();
        for (ISemanticNode<IWord> node : semanticNodes)
            node.item().getAllTokens().stream().forEach(token -> reverseIndex.put(token, node));
        return reverseIndex;
    }

    public static SemanticDict create(Set<ISemanticNode<IWord>> semanticNodes) {
        SemanticDict d = new SemanticDict(semanticNodes, generateReverseIndex(semanticNodes));
        return d;
    }
}
