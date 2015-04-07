package io.aif.language.fact;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Fact implements IFact {

    private final List<ISemanticNode<IWord>> semanticSentence;

    public Fact(List<ISemanticNode<IWord>> semanticSentence) {
        this.semanticSentence = semanticSentence;
    }

    public List<ISemanticNode<IWord>> getSemanticSentence() {
        return semanticSentence;
    }

    public Set<ISemanticNode<IWord>> getProperNouns() {
        //TODO The assumption here is that a sentence without a proper noun is not a fact.
        return semanticSentence
                .stream()
                .filter(node -> node.isProperNoun().isTrue())
                .collect(Collectors.toSet());
    }

    public boolean hasProperNoun(ISemanticNode<IWord> properNoun) {
        return getProperNouns().contains(properNoun);
    }

    public boolean hasProperNouns(Set<ISemanticNode<IWord>> properNounsToCheck) {
        for (ISemanticNode<IWord> properNoun : getProperNouns())  {
            if (!properNounsToCheck.contains(properNoun))
                return false;
        }
        return true;
    }
}
