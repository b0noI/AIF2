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
        final String inputText = "The JDK contains many terminal operations (such as average, sum, min, max, and count) that return one value by combining the contents of a stream. ";
        final List<String> expectedResult = Arrays.asList(new String[]{"The", "JDK", "contains", "many"});

        final List<String> actualResult = tokenSplitter.split(inputText);

        for (int i = 0; i < expectedResult.size(); i++) {
            assertEquals(expectedResult.get(i), actualResult.get(i));
        }
    }

}
