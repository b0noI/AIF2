package com.aif.language.token;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestITokenSplit {

    @Test
    public void testAllTokinizers() {
        Arrays.asList(ITokenSeparatorExtractor.Type.values())
                .parallelStream()
                .forEach(type -> testTokinization(type.getInstance()));
    }

    public void testTokinization(final ITokenSeparatorExtractor testClass) {
        final TokenSplitter tokenSplitter = new TokenSplitter(testClass);
        final String inputText = "toke2, toke2. Token3\n token4";
        final List<String> expectedResult = Arrays.asList(new String[]{"toke2,", "toke2.", "Token3", "token4"});

        final List<String> actualResult = tokenSplitter.split(inputText);

        assertEquals(expectedResult, actualResult);
    }

}
