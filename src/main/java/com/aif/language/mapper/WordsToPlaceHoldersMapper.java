package com.aif.language.mapper;

import com.aif.language.mapper.IMapper;
import com.aif.language.word.AbstractWord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class WordsToPlaceHoldersMapper implements IMapper<List<List<AbstractWord>>, List<List<AbstractWord.WordPlaceHolder>>> {

    @Override
    public List<List<AbstractWord.WordPlaceHolder>> map(List<List<AbstractWord>> wordSentences) {
        DistinctWordList wordList = new DistinctWordList();
        wordSentences.forEach(sentence -> wordList.addAll(sentence));
        return wordSentences
                .stream()
                .map(sentence -> map_(sentence, wordList))
                .collect(Collectors.toList());
    }

    private List<AbstractWord.WordPlaceHolder> map_(List<AbstractWord> words, DistinctWordList wordList) {
        return words
                .stream()
                .map(word -> {
                    AbstractWord wordFromWordList = wordList.get(wordList.indexOf(word));
                    return wordFromWordList.new WordPlaceHolder(wordFromWordList.basicToken());
                })
                .collect(Collectors.toList());
    }


    //TODO: Use Set interface
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
