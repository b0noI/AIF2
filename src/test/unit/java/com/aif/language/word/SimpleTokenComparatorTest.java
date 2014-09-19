package com.aif.language.word;

import com.aif.language.word.ITokenComparator;
import com.aif.language.word.SimpleTokenComparator;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.FileAssert.fail;


public class SimpleTokenComparatorTest {

    @Test(groups = "unit-tests")
    public void testCompareSuccessful() {
        Double expected = 0.5;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("heya", "hoyo");
        assertEquals(expected, actual);
    }

    @Test(groups = "unit-tests")
    public void testCompareWordsWithNoCommonLetters() {
        Double expected = 0.0;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("Brad", "John");
        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testCompareTheSameWords() {
        Double expected = 1.0;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("Brad", "Brad");
        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testCompareWordsAnagrams() {
        Double expected = 1.0;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("dera", "dear");
        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testCompareCaseInsensitive() {
        Double expected = 1.0;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("d", "D");
        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testCompareNull() {
        ITokenComparator comparator = new SimpleTokenComparator();
        try {
            comparator.compare(null, null);
            fail();
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
    }

    @Test(groups = "unit-tests")
    public void testRepeatedLetters() {
        Double expected = 0.5;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("aaaaabcde", "aab");
        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testCompareAnagrams() throws Exception {
        Double expected = 1.0;
        ITokenComparator comparator = new SimpleTokenComparator();
        Double actual = comparator.compare("mate", "tame");
        assertEquals(actual, expected);
    }
}
