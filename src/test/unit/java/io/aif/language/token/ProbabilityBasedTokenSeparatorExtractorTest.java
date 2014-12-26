package io.aif.language.token;


import junit.framework.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;


public class ProbabilityBasedTokenSeparatorExtractorTest {

    @Test (groups = "unit-tests")
    public void testGetCharactersMappedToCount() throws Exception {
        // input parameter
        final List<Character> inputCharacters = Arrays.asList('a', 'a', 'A', ' ', ' ', ' ');

        final Map<Character, Integer> expectedResult = new HashMap<Character, Integer>(){{
            put('a', 2);
            put('A', 1);
            put(' ', 3);
        }};

        // mocks

        // expected result

        // creating instances

        // execution test
        final Map<Character, Integer> actualResult = ProbabilityBasedTokenSeparatorExtractor.getCharactersMappedToCount(inputCharacters);

        // asserts
        assertEquals(expectedResult, actualResult);

        // verify
    }

    @Test (groups = "unit-tests")
    public void testSortBySeparatorProbability() {
        // input parameter
        final Map<Character, Integer> inputCharactersMappedToCount = new HashMap<>();
        inputCharactersMappedToCount.put('a', 5);
        inputCharactersMappedToCount.put('b', 3);
        inputCharactersMappedToCount.put('c', 7);

        // mocks

        // expected result
        final List<Character> expectedResult = Arrays.asList(new Character[]{'c', 'a', 'b'});

        // creating instances

        // execution test
        final List<Character> actualResult = ProbabilityBasedTokenSeparatorExtractor.sortBySeparatorProbability(inputCharactersMappedToCount);

        // asserts
        Assert.assertEquals(expectedResult, actualResult);

        // verify
    }

    @Test (groups = "unit-tests")
    public void testExtract() throws Exception {
        // input arguments
        final String inputText = "token1 adddddddddd";

        // mocks

        // expected results
        final Optional<List<Character>> expectedResult = Optional.of(Arrays.asList(new Character[]{'d'}));

        // creating test instance
        final ITokenSeparatorExtractor tokenSeparatorExtractor = ITokenSeparatorExtractor.Type.PROBABILITY.getInstance();

        // execution test
        final Optional<List<Character>> actualResult = tokenSeparatorExtractor.extract(inputText);

        // result assert
        assertEquals(expectedResult, actualResult);

        // mocks verify
    }

}