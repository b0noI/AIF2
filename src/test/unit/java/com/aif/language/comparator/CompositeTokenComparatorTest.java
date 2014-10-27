package com.aif.language.comparator;

import org.testng.annotations.Test;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

public class CompositeTokenComparatorTest {

    @Test(groups = "unit-tests")
    public void testCompareIfGivenComparatorsAreCalled() throws Exception {
        String t1 = "test1";
        String t2 = "test2";
        final ITokenComparator mockTokenComparator1 = mock(ITokenComparator.class);
        final ITokenComparator mockTokenComparator2 = mock(ITokenComparator.class);
        Double expected = 1.0;

        when(mockTokenComparator1.compare(any(), any())).thenReturn(1.0);
        when(mockTokenComparator2.compare(any(), any())).thenReturn(1.0);

        List<Map.Entry<ITokenComparator, Double>> comparatorList = Arrays.asList(
            new SimpleEntry<>(mockTokenComparator1, 1.0),
            new SimpleEntry<>(mockTokenComparator2, 1.0)
        );
        CompositeTokenComparator comparator = new CompositeTokenComparator(comparatorList);
        Double actual = comparator.compare(t1, t2);
        assertEquals(actual, expected);

        verify(mockTokenComparator1, times(1)).compare(t1, t2);
        verify(mockTokenComparator2, times(1)).compare(t1, t2);
    }

    @Test(groups = "unit-tests")
    public void testCompareWithUnEquallyWeightedComparators() throws Exception {
        String t1 = "test1";
        String t2 = "test2";
        final ITokenComparator mockTokenComparator1 = mock(ITokenComparator.class);
        final ITokenComparator mockTokenComparator2 = mock(ITokenComparator.class);
        Double expected = 1.5;

        when(mockTokenComparator1.compare(any(), any())).thenReturn(1.0);
        when(mockTokenComparator2.compare(any(), any())).thenReturn(1.0);

        List<Map.Entry<ITokenComparator, Double>> comparatorList = Arrays.asList(
                new SimpleEntry<>(mockTokenComparator1, 1.0),
                new SimpleEntry<>(mockTokenComparator2, 2.0)
        );
        CompositeTokenComparator comparator = new CompositeTokenComparator(comparatorList);
        Double actual = comparator.compare(t1, t2);
        assertEquals(actual, expected);

        verify(mockTokenComparator1, times(1)).compare(t1, t2);
        verify(mockTokenComparator2, times(1)).compare(t1, t2);
    }

    @Test(groups = "unit-tests")
    public void testComparatorConstructionWithoutComparatorsList() throws Exception {
        new CompositeTokenComparator();
    }

    @Test(groups = "unit-tests")
    public void testComparatorSetComparators() throws Exception {
        String t1 = "test1";
        String t2 = "test2";

        final ITokenComparator mockTokenComparator1 = mock(ITokenComparator.class);
        final ITokenComparator mockTokenComparator2 = mock(ITokenComparator.class);
        Double expected = 1.5;

        when(mockTokenComparator1.compare(any(), any())).thenReturn(1.0);
        when(mockTokenComparator2.compare(any(), any())).thenReturn(1.0);
        List<Map.Entry<ITokenComparator, Double>> comparatorList = Arrays.asList(
                new SimpleEntry<>(mockTokenComparator1, 1.0),
                new SimpleEntry<>(mockTokenComparator2, 2.0)
        );
        CompositeTokenComparator comparator = new CompositeTokenComparator();
        comparator.setComparators(comparatorList);

        Double actual = comparator.compare(t1, t2);
        assertEquals(actual, expected);

        verify(mockTokenComparator1, times(1)).compare(t1, t2);
        verify(mockTokenComparator2, times(1)).compare(t1, t2);
    }
}