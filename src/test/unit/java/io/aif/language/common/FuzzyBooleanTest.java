package io.aif.language.common;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FuzzyBooleanTest {

    @Test(groups = "unit-tests")
    public void testConstructionWithValue() throws Exception {
        IFuzzyBoolean fb = new FuzzyBoolean(0.4);
        assertEquals(fb.getValue(), 0.4);
    }

    @Test(groups = "unit-tests")
    public void testConstructionWithValueAndThreshold() throws Exception {
        IFuzzyBoolean fb = new FuzzyBoolean(0.4, 0.3);
        assertEquals(fb.getValue(), 0.4);
        assertEquals(fb.getThreshold(), 0.3);
        assertTrue(fb.isTrue());
    }

    @Test(groups = "unit-tests")
    public void testEqualsAndHashCode() throws Exception {
        IFuzzyBoolean fb = new FuzzyBoolean(0.4, 0.3);
        IFuzzyBoolean fb2 = new FuzzyBoolean(0.4, 0.3);
        assertTrue(fb.equals(fb2));
        assertEquals(fb.hashCode(), fb2.hashCode());
    }

    @Test(groups = "unit-tests")
    public void testEqualsAndHashCodeFailsByThreshold() throws Exception {
        IFuzzyBoolean fb = new FuzzyBoolean(0.4, 0.2);
        IFuzzyBoolean fb2 = new FuzzyBoolean(0.4, 0.3);
        assertFalse(fb.equals(fb2));
        assertNotEquals(fb.hashCode(), fb2.hashCode());
    }

    @Test(groups = "unit-tests")
    public void testEqualsHashCodeFailsByValue() throws Exception {
        IFuzzyBoolean fb = new FuzzyBoolean(0.5, 0.3);
        IFuzzyBoolean fb2 = new FuzzyBoolean(0.4, 0.3);
        assertFalse(fb.equals(fb2));
        assertNotEquals(fb.hashCode(), fb2.hashCode());
    }

    @Test(groups = "unit-tests")
    public void testIsTrue() throws Exception {
        IFuzzyBoolean fb = new FuzzyBoolean(0.5, 0.3);
        assertTrue(fb.isTrue());
    }

    @Test(groups = "unit-tests")
    public void testIsTrueWhenValueAndThresholdAreEqual() throws Exception {
        IFuzzyBoolean fb = new FuzzyBoolean(0.3, 0.3);
        assertTrue(fb.isTrue());
    }

    @Test(groups = "unit-tests")
    public void testIsTrueFail() throws Exception {
        IFuzzyBoolean fb = new FuzzyBoolean(0.2, 0.3);
        assertFalse(fb.isTrue());
    }
}