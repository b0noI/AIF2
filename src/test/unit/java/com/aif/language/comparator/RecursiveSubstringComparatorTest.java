package com.aif.language.comparator;

import com.aif.language.comparator.RecursiveSubstringComparator;
import org.testng.annotations.Test;
import com.aif.language.comparator.ITokenComparator;

import static org.testng.Assert.*;


public class RecursiveSubstringComparatorTest {

    private void testIt(final String t1, final String t2, double sumOfLettersOfCommonWords) {
        final double expected = (double)(sumOfLettersOfCommonWords * 2) / (t1.length() + t2.length());

        ITokenComparator comparator = new RecursiveSubstringComparator();
        final double actual = comparator.compare(t1, t2);
        assertEquals(actual, expected);
    }

    @Test(groups = "unit-test")
    public void testCompare() throws Exception {
        testIt("glucose", "fructice", 3);
    }

    @Test(groups = "unit-test")
    public void testCompareStringsWithRecursionOnRightSide() throws Exception {
        testIt("french", "fresh", 4);
    }

    @Test(groups = "unit-test")
    public void testCompareStringsWithRecursionOnLeftSide() throws Exception {
       testIt("branch", "bench", 4);
    }

    @Test(groups = "unit-test")
    public void testCompareSingleCharacterCommonSubstring() throws Exception {
        testIt("mate", "tame", 2);
    }

    @Test(groups = "unit-test")
    public void testCompareSameString() throws Exception {
        testIt("best", "best", 4);
    }

    @Test(groups = "unit-test")
    public void testCompareOneStringSubstringOfTheOther() throws Exception {
        testIt("batman", "man", 3);
    }

    @Test(groups = "unit-test")
    public void testCompareOneStringSubstringOfTheOtherOrderChanged() throws Exception {
        testIt("man", "batman", 3);
    }

    @Test(groups = "unit-test")
    public void testCompareNoCommonSubstring() throws Exception {
        testIt("man", "hid", 0);
    }

    @Test(groups = "unit-test")
    public void testCompareSingleCharacterCommonString() throws Exception {
        testIt("man", "had", 1);
    }

    @Test(groups = "unit-test")
    public void testCompareVeryLongStringsNoCommon() throws Exception {
        testIt("abcdefghiklmnopqrtuvzyzabcedfghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrsuaswrgss",
                "!@#$%^&*()_+<>?:|}{+++++},,,,~~~~±±±±±{}{}___)(*&^%$#@!1234567890121212343445566778989``````;;';';';",
                0);
    }

    @Test(groups = "unit-test")
    public void testCompareEmptyStrings() throws Exception {
        testIt("", "", 0);
    }

    @Test(groups = "unit-test")
    public void testCompareNullFirstInput() throws Exception {
        try {
            testIt(null, "sdsd", 0);
        } catch (NullPointerException e) { }
    }

    @Test(groups = "unit-test")
    public void testCompareNullSecondInput() throws Exception {
        try {
            testIt("sdsdsd", null, 0);
        } catch (NullPointerException e) { }
    }
}
