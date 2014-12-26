package io.aif.language.token.comparator;

import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class RecursiveSubstringComparatorTest {

    private void testIt(final String t1, final String t2, double sumOfLettersOfCommonWords) {
        final double expected = (sumOfLettersOfCommonWords * 2) / (t1.length() + t2.length());

        ITokenComparator comparator = ITokenComparator.Type.RECURSIVE_SUBSTRING_COMPARATOR.getInstance();
        final double actual = comparator.compare(t1, t2);
        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testCompare() throws Exception {
        testIt("glucose", "fructice", 3);
    }

    @Test(groups = "unit-tests")
    public void testCompareStringsWithRecursionOnRightSide() throws Exception {
        testIt("french", "fresh", 4);
    }

    @Test(groups = "unit-tests")
    public void testCompareStringsWithRecursionOnLeftSide() throws Exception {
       testIt("branch", "bench", 4);
    }

    @Test(groups = "unit-tests")
    public void testCompareSingleCharacterCommonSubstring() throws Exception {
        testIt("mate", "tame", 2);
    }

    @Test(groups = "unit-tests")
    public void testCompareSameString() throws Exception {
        testIt("best", "best", 4);
    }

    @Test(groups = "unit-tests")
    public void testCompareOneStringSubstringOfTheOther() throws Exception {
        testIt("batman", "man", 3);
    }

    @Test(groups = "unit-tests")
    public void testCompareOneStringSubstringOfTheOtherOrderChanged() throws Exception {
        testIt("man", "batman", 3);
    }

    @Test(groups = "unit-tests")
    public void testCompareNoCommonSubstring() throws Exception {
        testIt("man", "hid", 0);
    }

    @Test(groups = "unit-tests")
    public void testCompareSingleCharacterCommonString() throws Exception {
        testIt("man", "had", 1);
    }

    @Test(groups = "unit-tests")
    public void testCompareVeryLongStringsNoCommon() throws Exception {
        testIt("abcdefghiklmnopqrtuvzyzabcedfghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrsuaswrgss",
                "!@#$%^&*()_+<>?:|}{+++++},,,,~~~~±±±±±{}{}___)(*&^%$#@!1234567890121212343445566778989``````;;';';';",
                0);
    }

    @Test(groups = "unit-tests")
    public void testCompareEmptyStrings() throws Exception {
        testIt("", "", 0);
    }

    @Test(groups = "unit-tests")
    public void testCompareNullFirstInput() throws Exception {
        try {
            testIt(null, "sdsd", 0);
            assert false;
        } catch (NullPointerException e) {
            assert true;
        }
    }

    @Test(groups = "unit-tests")
    public void testCompareNullSecondInput() throws Exception {
        try {
            testIt("sdsdsd", null, 0);
            assert false;
        } catch (NullPointerException e) {
            assert true;
        }
    }
}
