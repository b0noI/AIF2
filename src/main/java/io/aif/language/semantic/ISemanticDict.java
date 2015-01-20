package io.aif.language.semantic;

import io.aif.language.common.IDict;
import io.aif.language.word.IWord;

import java.util.Set;

/**
 * Created by vsk on 1/20/15.
 */
public interface ISemanticDict extends IDict<ISemanticNode<IWord>> {

    public Set<ISemanticNode<IWord>> getWords();

}
