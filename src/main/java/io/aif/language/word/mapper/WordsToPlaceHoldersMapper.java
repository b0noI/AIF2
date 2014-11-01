package io.aif.language.word.mapper;

import io.aif.language.word.AbstractWord;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.stream.Collectors;


public class WordsToPlaceHoldersMapper {

    public List<List<IWord.IWordPlaceholder>> map(final List<List<String>> sentences, final List<IWord> words) {
        final List<AbstractWord> abstractWords = words.stream().map(word -> (AbstractWord) word).collect(Collectors.toList());

        return sentences
                .stream()
                .map(sentence -> mapSentence(sentence, abstractWords))
                .collect(Collectors.toList());
    }

    private List<IWord.IWordPlaceholder> mapSentence(List<String> tokens, List<AbstractWord> abstractWords) {
        return tokens
                .stream()
                .map(token -> abstractWords.get(abstractWords.indexOf(token)).new WordPlaceHolder(token))
                .collect(Collectors.toList());
    }






}
