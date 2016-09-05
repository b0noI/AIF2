package io.aif.language.token.comparator;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

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

    final Map<ITokenComparator, Double> comparatorList
        = new HashMap<ITokenComparator, Double>() {
      {
        put(mockTokenComparator1, 1.0);
        put(mockTokenComparator2, 1.0);
      }
    };
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

    final Map<ITokenComparator, Double> comparatorList
        = new HashMap<ITokenComparator, Double>() {
      {
        put(mockTokenComparator1, 1.0);
        put(mockTokenComparator2, 2.0);
      }
    };
    CompositeTokenComparator comparator = new CompositeTokenComparator(comparatorList);
    Double actual = comparator.compare(t1, t2);
    assertEquals(actual, expected);

    verify(mockTokenComparator1, times(1)).compare(t1, t2);
    verify(mockTokenComparator2, times(1)).compare(t1, t2);
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
    final Map<ITokenComparator, Double> comparatorList
        = new HashMap<ITokenComparator, Double>() {
      {
        put(mockTokenComparator1, 1.0);
        put(mockTokenComparator2, 2.0);
      }
    };
    ITokenComparator comparator = ITokenComparator.createComposite(comparatorList);

    Double actual = comparator.compare(t1, t2);
    assertEquals(actual, expected);

    verify(mockTokenComparator1, times(1)).compare(t1, t2);
    verify(mockTokenComparator2, times(1)).compare(t1, t2);
  }
}