package io.aif.language.ner.noun;

import io.aif.language.ner.noun.IProperNounSet;
import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class TitleCaseProperNounCalculatorTest {

    @Test(groups = "unit-tests")
    public void testCalculate() throws Exception {
        final List<String> tokens = Arrays.asList("Heya", "Hiya", "Hola", "deela");
        final String rootToken = "Heya";

        final double expected = .75d;

        final IWord mockWord = mock(IWord.class);
        when(mockWord.getAllTokens()).thenReturn(new HashSet<>(tokens));
        when(mockWord.getRootToken()).thenReturn(rootToken);

        final IProperNounSet calculator = new TitleCaseProperNounSet();
        final double actual = calculator.contains(mockWord).getValue();

        assertEquals(Double.compare(actual, expected), 0);
    }

    @Test(groups = "unit-tests")
    public void testCalculateWithEmptyWord() throws Exception {
        final IWord mockWord = mock(IWord.class);
        final IProperNounSet calculator = new TitleCaseProperNounSet();
        final double actual = calculator.contains(mockWord).getValue();
        assertEquals(Double.compare(actual, 0.0d), 0);
    }

    @Test(groups = "unit-tests")
    public void testCalculateWithNoTitleCaseTokens() throws Exception {
        final List<String> tokens = Arrays.asList("heya", "hiya", "hola", "deela");
        final String rootToken = "heya";

        final double expected = .0d;

        final IWord mockWord = mock(IWord.class);
        when(mockWord.getAllTokens()).thenReturn(new HashSet<>(tokens));
        when(mockWord.getRootToken()).thenReturn(rootToken);

        final IProperNounSet calculator = new TitleCaseProperNounSet();
        final double actual = calculator.contains(mockWord).getValue();

        assertEquals(Double.compare(actual, expected), 0);
    }

    @Test(groups = "unit-tests")
    public void testCalculateWithFewTitleCaseTokens() throws Exception {
        final List<String> tokens = Arrays.asList("Heya", "hiya", "hola", "deela");
        final String rootToken = "Heya";

        final double expected = .25d;

        final IWord mockWord = mock(IWord.class);
        when(mockWord.getAllTokens()).thenReturn(new HashSet<>(tokens));
        when(mockWord.getRootToken()).thenReturn(rootToken);

        final IProperNounSet calculator = new TitleCaseProperNounSet();
        final double actual = calculator.contains(mockWord).getValue();

        assertEquals(Double.compare(actual, expected), 0);
    }

}