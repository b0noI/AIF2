package io.aif.language.token.separator;

import io.aif.language.token.separator.ITokenSeparatorExtractor;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;

public class PredefinedTokenSeparatorExtractorTest {

    @Test(groups = "unit-tests")
    public void testExtract() throws Exception {
        // input parameter
        final String inputText = null;

        // mocks

        // expected result
        final Optional<List<Character>> expectedResult = Optional.of(
                Arrays.asList(new Character[]{' ', '\n', System.lineSeparator().charAt(0)}));

        // creating instances
        final ITokenSeparatorExtractor testInstance = ITokenSeparatorExtractor.Type.PREDEFINED.getInstance();

        // execution test
        final Optional<List<Character>> actualResult = testInstance.extract(inputText);

        // asserts
        assertEquals(expectedResult, actualResult);

        // verify
    }

}