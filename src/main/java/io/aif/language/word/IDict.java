package io.aif.language.word;


import java.util.Iterator;
import java.util.Set;

public interface IDict extends Iterable<IWord> {

    public Set<IWord> getWords();

    default public Iterator<IWord> iterator() {
        return getWords().iterator();
    }

}
