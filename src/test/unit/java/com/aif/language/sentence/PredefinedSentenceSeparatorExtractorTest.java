package com.aif.language.sentence;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class PredefinedSentenceSeparatorExtractorTest {

    @Test(groups = "unit-tests")
    public void testExtract() throws Exception {
        // input arguments
        final List<String> inputTokens = null;

        // mocks

        // expected results
        final Optional<List<Character>> expectedResult = Optional.of(Arrays.asList(new Character[]{
                '.', '!', '?',
                '(', ')', '[',
                ']', '{', '}',
                ';', '\'', '\"'
        }));

        // creating test instance
        final ISentenceSeparatorExtractor sentenceSeparatorExtractor = ISentenceSeparatorExtractor.Type.PREDEFINED.getInstance();

        // execution test
        final Optional<List<Character>> actualResult = sentenceSeparatorExtractor.extract(inputTokens);

        // result assert
        assertEquals(expectedResult, actualResult);

        // mocks verify
    }

}