package com.aif.language.word;

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

public class WordTest {

    @Test (groups = "unit-tests")
    public void testWordConstruction() throws Exception {
        String token = "hello";
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        Set<String> expectedTokens = new HashSet<>();
        expectedTokens.add(token);
        long expectedTokenCount = 1;

        Word word = new Word(token, mockTokenComparator);
        Set<String> actualTokens =  word.getTokens();
        long actualTokenCount = word.tokenCount(token);
        assertEquals(actualTokens, expectedTokens);
        assertEquals(actualTokenCount, expectedTokenCount);
    }

    @Test (groups = "unit-tests")
    public void testEqualTrue() throws Exception {
        String t1 = "hello";
        String t2 = "chello";
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        when(mockTokenComparator.compare(t1, t2)).thenReturn(0.8);

        Word word1 = new Word(t1, mockTokenComparator);
        Word word2 = new Word(t2, mockTokenComparator);
        boolean actual = word1.equals(word2);

        assertTrue(actual);
        verify(mockTokenComparator, times(1)).compare(t1, t2);
    }

    @Test (groups = "unit-tests")
    public void testEqualFalse() throws Exception {
        String t1 = "hello";
        String t2 = "grant";
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        when(mockTokenComparator.compare(t1, t2)).thenReturn(0.0);

        Word word1 = new Word(t1, mockTokenComparator);
        Word word2 = new Word(t2, mockTokenComparator);
        boolean actual = word1.equals(word2);

        assertFalse(actual);
        verify(mockTokenComparator).compare(t1, t2);
    }

    @Test(groups = "unit-tests")
    public void testEqualWithMultipleTokensInEachWord() throws Exception {
        List<String> tokens1 = new ArrayList(Arrays.asList(new String[]{"hello", "chello", "yellow"}));
        List<String> tokens2 = new ArrayList(Arrays.asList(new String[]{"trello", "lolo", "halo"}));
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        when(mockTokenComparator.compare(any(), any())).thenReturn(0.8);

        Word word1 = new Word(tokens1.remove(0), mockTokenComparator);
        tokens1.forEach(token -> word1.merge(new Word(token, mockTokenComparator)));

        Word word2 = new Word(tokens2.remove(0), mockTokenComparator);
        tokens2.forEach(token -> word2.merge(new Word(token, mockTokenComparator)));

        boolean actual = word1.equals(word2);
        assertTrue(actual);
    }

    @Test(groups = "unit-tests")
    public void testEqualWithMultipleTokensInEachWordReturnsFalse() throws Exception {
        List<String> tokens1 = new ArrayList(Arrays.asList(new String[]{"hello", "chello", "yellow"}));
        List<String> tokens2 = new ArrayList(Arrays.asList(new String[]{"bing", "bong", "bam"}));
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        when(mockTokenComparator.compare(any(), any())).thenReturn(0.1);

        Word word1 = new Word(tokens1.remove(0), mockTokenComparator);
        tokens1.forEach(token -> word1.merge(new Word(token, mockTokenComparator)));

        Word word2 = new Word(tokens2.remove(0), mockTokenComparator);
        tokens2.forEach(token -> word2.merge(new Word(token, mockTokenComparator)));

        boolean actual = word1.equals(word2);
        assertFalse(actual);
    }

    @Test (groups = "unit-tests")
    public void testMerge() throws Exception {
        String t1 = "hello";
        String t2 = "chello";
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        Set<String> expectedTokens = new HashSet<String>(){{
            add(t1);
            add(t2);
        }};

        Word word1 = new Word(t1, mockTokenComparator);
        Word word2 = new Word(t2, mockTokenComparator);
        word1.merge(word2);
        Set<String> actualTokens = word1.getTokens();

        assertEquals(actualTokens, expectedTokens);
    }

    @Test(groups = "unit-tests")
    public void testMergeWordsWithMultipleTokens() throws Exception {
        List<String> tokens1 = new ArrayList(Arrays.asList(new String[]{"hello", "chello", "yellow"}));
        List<String> tokens2 = new ArrayList(Arrays.asList(new String[]{"bing", "bong", "bam"}));
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        Set<String> expectedTokens = new HashSet<>();
        tokens1.forEach(token -> expectedTokens.add(token));
        tokens2.forEach(token -> expectedTokens.add(token));

        Word word1 = new Word(tokens1.remove(0), mockTokenComparator);
        tokens1.forEach(token -> word1.merge(new Word(token, mockTokenComparator)));

        Word word2 = new Word(tokens2.remove(0), mockTokenComparator);
        tokens2.forEach(token -> word2.merge(new Word(token, mockTokenComparator)));

        word1.merge(word2);
        Set<String> actualTokens = word1.getTokens();
        assertEquals(actualTokens, expectedTokens);
    }
}
