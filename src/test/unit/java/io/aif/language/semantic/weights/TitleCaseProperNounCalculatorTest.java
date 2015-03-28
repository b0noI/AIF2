package io.aif.language.semantic.weights;

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
        List<String> tokens = Arrays.asList("Heya", "Hiya", "Hola", "deela");
        String rootToken = "Heya";

        double expected = .75d;

        IWord mockWord = mock(IWord.class);
        when(mockWord.getAllTokens()).thenReturn(new HashSet<String>(tokens));
        when(mockWord.getRootToken()).thenReturn(rootToken);

        IProperNounCalculator calculator = new TitleCaseProperNounCalculator();
        double actual = calculator.calculate(mockWord);

        assertEquals(Double.compare(actual, expected), 0);
    }

    @Test(groups = "unit-tests")
    public void testCalculateWithEmptyWord() throws Exception {
        IWord mockWord = mock(IWord.class);
        IProperNounCalculator calculator = new TitleCaseProperNounCalculator();
        double actual = calculator.calculate(mockWord);
        assertEquals(Double.compare(actual, 0.0d), 0);
    }

    @Test(groups = "unit-tests")
    public void testCalculateWithNoTitleCaseTokens() throws Exception {
        List<String> tokens = Arrays.asList("heya", "hiya", "hola", "deela");
        String rootToken = "heya";

        double expected = .0d;

        IWord mockWord = mock(IWord.class);
        when(mockWord.getAllTokens()).thenReturn(new HashSet<String>(tokens));
        when(mockWord.getRootToken()).thenReturn(rootToken);

        IProperNounCalculator calculator = new TitleCaseProperNounCalculator();
        double actual = calculator.calculate(mockWord);

        assertEquals(Double.compare(actual, expected), 0);
    }

    @Test(groups = "unit-tests")
    public void testCalculateWithFewTitleCaseTokens() throws Exception {
        List<String> tokens = Arrays.asList("Heya", "hiya", "hola", "deela");
        String rootToken = "Heya";

        double expected = .25d;

        IWord mockWord = mock(IWord.class);
        when(mockWord.getAllTokens()).thenReturn(new HashSet<String>(tokens));
        when(mockWord.getRootToken()).thenReturn(rootToken);

        IProperNounCalculator calculator = new TitleCaseProperNounCalculator();
        double actual = calculator.calculate(mockWord);

        assertEquals(Double.compare(actual, expected), 0);
    }
}