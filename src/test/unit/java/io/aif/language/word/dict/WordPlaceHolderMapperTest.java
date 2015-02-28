package io.aif.language.word.dict;

import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class WordPlaceHolderMapperTest {

    @Test(groups = "unit-tests")
    public void testMap() throws Exception {
        Collection<String> inputTokens = Arrays.asList("I", "am", "from", "kandy", "land.", "And", "you?");
        Set<IWord> words = new HashSet<>();
        inputTokens.forEach(token -> words.add(new Word(token, Arrays.asList(token), 1l)));
        Dict inputDict = Dict.create(words);

        List<IWord.IWordPlaceholder> expected = inputTokens
                .stream()
                .map(token -> {
                    Word word = (Word) inputDict.search(token).get();
                    return word.new WordPlaceholder(token);
                })
                .collect(Collectors.toList());

        WordPlaceHolderMapper mapper = new WordPlaceHolderMapper(inputDict);
        Collection<IWord.IWordPlaceholder> actual = mapper.map(inputTokens);
        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests", expectedExceptions = RuntimeException.class)
    public void testMapTokenNotFoundInISearchable() throws Exception {
        Collection<String> tokens = Arrays.asList("I", "am", "from", "kandy", "land.", "And", "you?");
        Collection<String> input = new ArrayList<>(tokens.size());
        tokens.forEach(token -> input.add(token));
        input.add("Hoppa!");

        Set<IWord> words = new HashSet<>();
        tokens.forEach(token -> words.add(new Word(token, Arrays.asList(token), 1l)));
        Dict inputDict = Dict.create(words);

        WordPlaceHolderMapper mapper = new WordPlaceHolderMapper(inputDict);
        mapper.map(input);
    }
}