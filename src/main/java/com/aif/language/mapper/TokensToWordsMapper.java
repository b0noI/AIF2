package com.aif.language.mapper;

import com.aif.language.mapper.IMapper;
import com.aif.language.word.AbstractWord;
import com.aif.language.word.ITokenComparator;
import com.aif.language.word.Word;

import java.util.List;
import java.util.stream.Collectors;


public class TokensToWordsMapper implements IMapper<List<String>, List<AbstractWord>> {

    private ITokenComparator comparator;

    public TokensToWordsMapper(ITokenComparator comparator) {
        this.comparator = comparator;
    }

    public List<AbstractWord> map(List<String> tokens) {
        return tokens
                .stream()
                .map(token -> new Word(token, comparator))
                .collect(Collectors.toList());
    }

}
