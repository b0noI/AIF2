package io.aif.language.fact;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.Set;

public interface IFact {

    public List<ISemanticNode<IWord>> getSemanticSentence();

    public Set<ISemanticNode<IWord>> getProperNouns();

    public boolean hasProperNoun(ISemanticNode<IWord> properNoun);

    public boolean hasProperNouns(Set<ISemanticNode<IWord>> properNouns);
}
