package com.aif.language.token;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProbabilityBasedTokenSeparatorExtractorTest {

    @Test
    public void testGetCharactersMappedToCount() throws Exception {
        final List<Character> inputCharacters = Arrays.asList('a', 'a', 'A', ' ', ' ', ' ');

        final Map<Character, Integer> expectedResult = new HashMap<Character, Integer>(){{
            put('a', 2);
            put('A', 1);
            put(' ', 3);
        }};

        final Map<Character, Integer> actualResult = ProbabilityBasedTokenSeparatorExtractor.getCharactersMappedToCount(inputCharacters);

        assertEquals(expectedResult, actualResult);
    }
}