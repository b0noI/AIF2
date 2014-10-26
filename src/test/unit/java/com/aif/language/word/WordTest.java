package com.aif.language.word;

import com.aif.language.comparator.ITokenComparator;
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
    public void testEqualityOfSameTokenButDifferentWordObject() throws Exception {
        String token = "hello";
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        when(mockTokenComparator.compare(token, token)).thenReturn(1.0);
        Word word1 = new Word(token, mockTokenComparator);
        Word word2 = new Word(token, mockTokenComparator);
        assertEquals(word1, word2);
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

    @Test(groups = "unit-tests")
    public void testBasicTokenWithSingleToken() throws Exception {
        String expected = "test";
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        Word word = new Word(expected, mockTokenComparator);
        String actual = word.basicToken();
        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testBasicTokenWithTwoTokens() throws Exception {
        String expected1 = "best";
        String expected2 = "tested";
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        when(mockTokenComparator.compare(anyString(), anyString())).thenReturn(0.1);

        Word word1 = new Word(expected1, mockTokenComparator);
        Word word2 = new Word(expected2, mockTokenComparator);
        word1.merge(word2);
        String actual = word1.basicToken();
        assertEquals(actual, expected2);
    }

    @Test(groups = "unit-tests")
    public void testBasicTokenWithTThreeTokens() throws Exception {
        String expected1 = "best";
        String expected2 = "tested";
        String expected3 = "boost";
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        when(mockTokenComparator.compare(eq(expected3), any())).thenReturn(0.1);
        when(mockTokenComparator.compare(eq(expected1), any())).thenReturn(0.5);
        when(mockTokenComparator.compare(eq(expected2), any())).thenReturn(0.5);

        Word word1 = new Word(expected1, mockTokenComparator);
        word1.merge(new Word(expected2, mockTokenComparator));
        word1.merge(new Word(expected3, mockTokenComparator));
        String actual = word1.basicToken();
        assertEquals(actual, expected3);
    }

    @Test(groups = "unit-tests")
    public void testBasicTokenWithMultipleTokens() throws Exception {
        String expected1 = "best";
        String expected2 = "tested";
        String expected3 = "boost";
        String expected4 = "mate";
        String expected5 = "health";
        String expected6 = "brittle";
        final ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        when(mockTokenComparator.compare(any(), any())).thenReturn(0.5);
        when(mockTokenComparator.compare(eq(expected2), any())).thenReturn(0.1);

        Word word1 = new Word(expected1, mockTokenComparator);
        word1.merge(new Word(expected2, mockTokenComparator));
        word1.merge(new Word(expected3, mockTokenComparator));
        word1.merge(new Word(expected4, mockTokenComparator));
        word1.merge(new Word(expected5, mockTokenComparator));
        word1.merge(new Word(expected6, mockTokenComparator));
        String actual = word1.basicToken();
        assertEquals(actual, expected2);
    }
}
