package com.aif.language.word.stemmer;

import com.aif.language.common.IExtractor;
import com.aif.language.comparator.ITokenComparator;
import com.aif.language.word.AbstractWord;
import com.aif.language.word.IWord;
import com.aif.language.word.Word;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Stemmer implements IExtractor<Collection<String>, List<IWord>> {

    private ITokenComparator comparator;

    public Stemmer() {
        this(ITokenComparator.defaultComparator());
    }

    public Stemmer(final ITokenComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public Optional<List<IWord>> extract(Collection<String> from) {
        List<AbstractWord> words = from
            .stream()
            .map(token -> new Word(token, comparator))
            .collect(Collectors.toList());
        DistinctWordList wordList = new DistinctWordList();
        wordList.addAll(words);

        return Optional.of(words.stream().map(word -> (IWord) word).collect(Collectors.toList()));
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
