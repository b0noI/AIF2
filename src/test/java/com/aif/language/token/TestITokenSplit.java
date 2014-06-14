package com.aif.language.token;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestITokenSplit {

    @Test
    public void testAllTokinizers() {
        Arrays.asList(ITokenSplit.Type.values())
                .parallelStream()
                .forEach(type -> testTokinization(type.getInstance()));
    }

    public void testTokinization(final ITokenSplit testClass) {
        final String inputText = "toke2, toke2. Token3\n token4";
        final List<String> expectedResult = Arrays.asList(new String[]{"toke2,", "toke2.", "Token3", "token4"});

        final List<String> actualResult = testClass.parsTokens(inputText);

        assertEquals(expectedResult, actualResult);
    }

}
