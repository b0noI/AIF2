package io.aif.language.word.dict;

import io.aif.language.common.IGrouper;
import io.aif.language.common.settings.ISettings;
import io.aif.language.word.comparator.IGroupComparator;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Matchers.anySet;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

public class FormGrouperTest {

    private static final double GT_COMPARATOR_THRESHOLD = ISettings.SETTINGS.WordSetDictComparatorThreshold() + .1;
    private static final double LT_COMPARATOR_THRESHOLD = ISettings.SETTINGS.WordSetDictComparatorThreshold() - .1;

    @Test
    public void testGroupAllTokensAreSimilar() throws Exception {
        List<String> input = Arrays.asList("hey", "heya", "hiya", "iyah");
        List<Set<String>> expected = new ArrayList<>();
        expected.add(new HashSet<>(input));

        IGroupComparator groupComparator = mock(IGroupComparator.class);
        when(groupComparator.compare(anySet(), anySet())).thenReturn(GT_COMPARATOR_THRESHOLD);

        IGrouper grouper = new FormGrouper(groupComparator);
        List<Set<String>> actual = grouper.group(input);
        assertEquals(actual, expected);
    }

    @Test
    public void testGroupNoTokensAreSimilar() throws Exception {
        List<String> input = Arrays.asList("aa", "bbb", "cccc", "dddd");
        List<Set<String>> expected = new ArrayList<>();
        input.forEach(token -> expected.add(new HashSet<>(Arrays.asList(token))));

        IGroupComparator groupComparator = mock(IGroupComparator.class);
        when(groupComparator.compare(anySet(), anySet())).thenReturn(LT_COMPARATOR_THRESHOLD);

        IGrouper grouper = new FormGrouper(groupComparator);
        List<Set<String>> actual = grouper.group(input);
        assertEquals(actual, expected);
    }

    @Test
    public void testGroupSomeTokensAreSimilar() throws Exception {
        List<String> input = Arrays.asList("hi", "hiya", "cat", "catty", "brat");
        List<?> expected = new ArrayList<Set<String>>() {{
            add(new HashSet<>(Arrays.asList(input.get(0), input.get(1))));
            add(new HashSet<>(Arrays.asList(input.get(2), input.get(3))));
            add(new HashSet<>(Arrays.asList(input.get(4))));
        }};
        List<Set<String>> mockSets = new ArrayList<>();
        input.forEach(token -> mockSets.add(new HashSet<String>(Arrays.asList(token))));

        IGroupComparator groupComparator = mock(IGroupComparator.class);
        when(groupComparator.compare(mockSets.get(0), mockSets.get(1))).thenReturn(GT_COMPARATOR_THRESHOLD);
        when(groupComparator.compare(mockSets.get(2), mockSets.get(3))).thenReturn(GT_COMPARATOR_THRESHOLD);

        IGrouper grouper = new FormGrouper(groupComparator);
        List<Set<String>> actual = grouper.group(input);
        assertEquals(actual, expected);
    }
}