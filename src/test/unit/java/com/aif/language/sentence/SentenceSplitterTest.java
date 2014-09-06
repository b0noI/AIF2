package com.aif.language.sentence;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class SentenceSplitterTest {

    @Test
    public void testSplit() throws Exception {

    }

    @Test
    public void testPrepareSentences() throws Exception {

    }

    @Test
    public void testPrepareToken() throws Exception {

    }

    @Test(groups = "unit-tests")
    public void testMapToBooleans() throws Exception {
        // input arguments
        final List<String> inputTokens = Arrays.asList(new String[]{
                "token1",
                "tok.en",
                "tok.en.",
                "tok.en)",
                "(tok.en",
                "t(ok.en",
        });
        final List<Character> inputCharacters = Arrays.asList(new Character[]{
                '.', ')', '(', '!'
        });

        // mocks

        // expected results
        final List<Boolean> expectedResult = Arrays.asList(new Boolean[]{
            false, false, true, true, true, false
        });

        // creating test instance

        // execution test
        final List<Boolean> actualResult = SentenceSplitter.mapToBooleans(inputTokens, inputCharacters);

        // result assert
        assertEquals(expectedResult, actualResult);

        // mocks verify
    }

}