package com.aif.language.word;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Needs to perform 2 operations:
 *  1) Map tokens to words.
 *  2) 
 */

public class WordExtractor implements IWordExtractor {

    private ITokenComparator tokenComparator;

    public WordExtractor(ITokenComparator tokenComparator) {
        this.tokenComparator = tokenComparator;
    }

    @Override
    public List<List<AbstractWord.WordPlaceHolder>> getWords(List<List<String>> sentences) {
        WordList wordList = new WordList();
        List<List<Word>> wordSentences = mapTokensToWords(sentences);
        wordSentences.forEach(words -> wordList.addAll(words));
        return wordSentences
                .stream()
                .map(wordSentence -> map(wordSentence, wordList))
                .collect(Collectors.toList());
    }

    private List<AbstractWord.WordPlaceHolder> map(List<Word> words, WordList wordList) {
        return words
                .stream()
                .map(word -> {
                    Word wlw = wordList.get(wordList.indexOf(word));
                    return wlw.new WordPlaceHolder(wlw.basicToken());
                })
                .collect(Collectors.toList());
    }

    private List<List<Word>> mapTokensToWords(List<List<String>> sentences) {
        List<List<Word>> wordSentences = sentences.stream().<List<Word>>map(
                sentence -> sentence.stream().<Word>map(
                        token -> new Word((String)token, tokenComparator)
                ).collect(Collectors.toList())
        ).collect(Collectors.toList());
        return wordSentences;
    }

    private static class WordList extends ArrayList<Word> {

        @Override
        public boolean add(Word word) {
            final int index = indexOf(word);
            if (index != -1) {
                get(index).merge(word);
                return true;
            }

            return super.add(word);
        }

        @Override
        public boolean addAll(Collection<? extends Word> c) {
            c.forEach(element -> add(element));
            return true;
        }

    }

}
