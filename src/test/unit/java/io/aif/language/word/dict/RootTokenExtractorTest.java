package io.aif.language.word.dict;

import io.aif.language.token.comparator.ITokenComparator;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

public class RootTokenExtractorTest {

    @Test(groups = "unit-tests", enabled = false)
    public void testExtractEmptyInput() throws Exception {
        List<String> input = Arrays.asList();
        Optional expected = Optional.empty();

        ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        RootTokenExtractor extractor = new RootTokenExtractor(mockTokenComparator);
        Optional actual = extractor.extract(input);
        assertEquals(actual.get(), expected.get());
    }

    @Test(groups = "unit-tests")
    public void testExtractCollectionWithOneElement() throws Exception {
        List<String> input = Arrays.asList("hoppa");
        Optional<String> expected = Optional.of(input.get(0));

        ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        RootTokenExtractor extractor = new RootTokenExtractor(mockTokenComparator);
        Optional<String> actual = extractor.extract(input);
        assertEquals(actual.get(), expected.get());
    }

    @Test(groups = "unit-tests")
    public void testExtractAllComparisonReturnNull() throws Exception {
        List<String> input = Arrays.asList("hoppa", "hippa", "homppa");

        ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        when(mockTokenComparator.compare(any(), any())).thenReturn(0.0);
        RootTokenExtractor extractor = new RootTokenExtractor(mockTokenComparator);
        Optional<String> actual = extractor.extract(input);
        assertTrue(actual.isPresent());
        assertEquals(actual.get(), "hoppa");
    }

    @Test(groups = "unit-tests")
    public void testExtract() throws Exception {
        List<String> input = Arrays.asList("hi", "hiya", "heya");
        Optional<String> expected = Optional.of(input.get(0));

        ITokenComparator mockTokenComparator = mock(ITokenComparator.class);
        when(mockTokenComparator.compare(input.get(0), input.get(1))).thenReturn(0.1);
        when(mockTokenComparator.compare(input.get(0), input.get(2))).thenReturn(0.1);
        when(mockTokenComparator.compare(input.get(1), input.get(0))).thenReturn(0.2);
        when(mockTokenComparator.compare(input.get(1), input.get(2))).thenReturn(0.2);
        when(mockTokenComparator.compare(input.get(2), input.get(0))).thenReturn(0.3);
        when(mockTokenComparator.compare(input.get(2), input.get(1))).thenReturn(0.3);

        RootTokenExtractor extractor = new RootTokenExtractor(mockTokenComparator);
        Optional<String> actual = extractor.extract(input);
        verify(mockTokenComparator, times(1)).compare(input.get(0), input.get(1));
        assertEquals(actual.get(), expected.get());
    }
}