package io.aif.language.word.dict;

import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.AbstractWord;
import io.aif.language.word.IWord;
import io.aif.language.word.Word;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class DictBuilder implements IDictBuilder<Collection<String>> {

    private ITokenComparator comparator;

    public DictBuilder() {
        this(ITokenComparator.defaultComparator());
    }

    public DictBuilder(final ITokenComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public List<IWord> build(Collection<String> from) {
        List<AbstractWord> words = from
            .stream()
            .map(token -> new Word(token, comparator))
            .collect(Collectors.toList());
        DistinctWordList wordList = new DistinctWordList();
        wordList.addAll(words);

        return words.stream().map(word -> (IWord) word).collect(Collectors.toList());
    }

    private static class DistinctWordList extends ArrayList<AbstractWord> {

        @Override
        public boolean add(AbstractWord word) {
            final int index = indexOf(word);
            if (index != -1) {
                get(index).merge(word);
                return true;
            }
            return super.add(word);
        }

        @Override
        public boolean addAll(Collection<? extends AbstractWord> c) {
            c.forEach(element -> add(element));
            return true;
        }
    }

}
