package io.aif.language.common;

import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class SentenceMapperTest {

    @Test(groups = "unit-tests")
    public void testMap() throws Exception {
        List<String> tokens = Arrays.asList("Hello", "Mathew", "Ibrahim", "Went");
        ISearchable<String, String> searchable = mock(ISearchable.class);
        tokens.forEach(
                token -> when(searchable.search(token)).thenReturn(Optional.of(token))
        );
        SentenceMapper<String, String> mapper = new SentenceMapper<>(searchable);
        Object actual = mapper.map(tokens);
        assertEquals(actual, tokens);
    }

    @Test(groups = "unit-tests", expectedExceptions = NoSuchElementException.class)
    public void testMapFailMissingItemInSearchable() throws Exception {
        List<String> tokens = Arrays.asList("Hello", "Mathew", "Ibrahim", "Went");
        ISearchable<String, String> searchable = mock(ISearchable.class);
        tokens.forEach(
                token -> {
                    if (token.equals("Hello"))
                        when(searchable.search(token)).thenReturn(Optional.empty());
                    else
                        when(searchable.search(token)).thenReturn(Optional.of(token));
                }
        );
        SentenceMapper<String, String> mapper = new SentenceMapper<>(searchable);
        mapper.map(tokens);
    }

    @Test(groups = "unit-tests")
    public void testMapAll() throws Exception {
        List<List<String>> rawSentences = Arrays.asList(
                Arrays.asList("Hello", "Mathew", "Ibrahim", "Went"),
                Arrays.asList("Dingo", "Bongo", "Django", "Dracula")
        );
        List<List<String>> expected = rawSentences
                .parallelStream()
                .map(sentence -> new ArrayList<>(sentence))
                .collect(Collectors.toList());

        ISearchable<String, String> searchable = mock(ISearchable.class);
        rawSentences
                .stream()
                .flatMap(tokens -> tokens.stream())
                .forEach(token ->
                                when(searchable.search(token)).thenReturn(Optional.of(token))
                );
        SentenceMapper<String, String> mapper = new SentenceMapper<>(searchable);
        List<List<String>> actual = mapper.mapAll(rawSentences);
        assertEquals(actual, expected);
    }
}