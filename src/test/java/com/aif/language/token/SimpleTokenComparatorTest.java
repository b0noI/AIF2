package com.aif.language.token;

import com.aif.language.word.ITokenComparator;
import com.aif.language.word.SimpleTokenComparator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleTokenComparatorTest {

    @Test
    public void testCompareSuccessful() {
        Double expected = 0.5;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("heya", "hoyo");
        assertEquals(expected, actual);
    }

    @Test
    public void testCompareWordsWithNoCommonLetters() {
        Double expected = 0.0;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("Brad", "John");
        assertEquals(actual, expected);
    }

    @Test
    public void testCompareTheSameWords() {
        Double expected = 1.0;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("Brad", "Brad");
        assertEquals(actual, expected);
    }

    @Test
    public void testCompareWordsAnagrams() {
        Double expected = 1.0;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("dera", "dear");
        assertEquals(actual, expected);
    }

    @Test
    public void testCompareCaseInsensitive() {
        Double expected = 1.0;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("d", "D");
        assertEquals(actual, expected);
    }

    @Test(expected = NullPointerException.class)
    public void testCompareNull() {
        ITokenComparator comparator = new SimpleTokenComparator();
        comparator.compare(null, null);
    }

    @Test
    public void testRepeatedLetters() {
        Double expected = 0.5;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("aaaaabcde", "aab");
        assertEquals(actual, expected);
    }
}
