package io.aif.language.sentence.splitters;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by vsk on 10/3/14.
 */
public class AbstractSentenceSplitterTest {

    @Test(groups = "unit-tests")
    public void testPrepareSentences() throws Exception {
        // input arguments
        final List<String> inputTokens = Arrays.asList(new String[]{
                "token...",
                "(token",
                "(!.token..",
                "token",
                "tok!en"
        });
        final List<Character> inputCharacters = Arrays.asList(new Character[]{'(', '!', '.'});

        // mocks

        // expected results
        final List<String> expectedResults = Arrays.asList(new String[]{
                "token",
                "...",
                "(",
                "token",
                "(!.",
                "token",
                "..",
                "token",
                "tok!en"
        });

        // creating test instance

        // execution test
        final List<String> actualResults = AbstractSentenceSplitter.prepareSentences(inputTokens, inputCharacters);

        // result assert
        assertEquals(actualResults, expectedResults);

        // mocks verify
    }

    @Test(groups = "unit-tests")
    public void testPrepareToken() throws Exception {

        final List<String> inputTokens = Arrays.asList(new String[]{
                "token.",
                "(token",
                "tok.en",
                "tokeN"
        });
        final List<Character> inputCharacters = Arrays.asList(new Character[] {
                '.', '('
        });

        final List<List<String>> expectedResults = new ArrayList<List<String>>(){{
            add(Arrays.asList(new String[]{"token", "."}));
            add(Arrays.asList(new String[]{"(", "token"}));
            add(Arrays.asList(new String[]{"tok.en"}));
            add(Arrays.asList(new String[]{"tokeN"}));
        }
        };

        for (int i = 0; i < expectedResults.size(); i++) {
            final List<String> expectedResult = expectedResults.get(i);
            final String inputToke = inputTokens.get(i);
            testPrepareToken(inputToke, inputCharacters, expectedResult);
        }

    }


    @Test(groups = "unit-tests")
    public void testFirstNonSeparatorPositionWhenNoFirstCharacter() throws Exception {
        // input arguments
        final String inputToken = "token";
        final List<Character> inputCharacters = Arrays.asList(new Character[]{'.'});

        // mocks

        // expected results
        final int expectedResult = 0;

        // creating test instance

        // execution test
        final int actualResult = AbstractSentenceSplitter.firstNonSeparatorPosition(inputToken, inputCharacters);

        // result assert
        assertEquals(expectedResult, actualResult);

        // mocks verify

    }

    @Test(groups = "unit-tests")
    public void testFirstNonSeparatorPositionWhenFirstCharacter() throws Exception {
        // input arguments
        final String inputToken = ".!token";
        final List<Character> inputCharacters = Arrays.asList(new Character[]{'.', '!'});

        // mocks

        // expected results
        final int expectedResult = 2;

        // creating test instance

        // execution test
        final int actualResult = AbstractSentenceSplitter.firstNonSeparatorPosition(inputToken, inputCharacters);

        // result assert
        assertEquals(actualResult, expectedResult);

        // mocks verify

    }

    @Test(groups = "unit-tests")
    public void testLastNonSeparatorPositionWhenLastCharacter() throws Exception {
        // input arguments
        final String inputToken = ".token!";
        final List<Character> inputCharacters = Arrays.asList(new Character[]{'.', '!'});

        // mocks

        // expected results
        final int expectedResult = 6;

        // creating test instance

        // execution test
        final int actualResult = AbstractSentenceSplitter.lastNonSeparatorPosition(inputToken, inputCharacters);

        // result assert
        assertEquals(expectedResult, actualResult);

        // mocks verify

    }

    @Test(groups = "unit-tests")
    public void testLastNonSeparatorPositionWhenNoLastCharacter() throws Exception {
        // input arguments
        final String inputToken = ".token";
        final List<Character> inputCharacters = Arrays.asList(new Character[]{'.'});

        // mocks

        // expected results
        final int expectedResult = 6;

        // creating test instance

        // execution test
        final int actualResult = AbstractSentenceSplitter.lastNonSeparatorPosition(inputToken, inputCharacters);

        // result assert
        assertEquals(expectedResult, actualResult);

        // mocks verify

    }

    private static void testPrepareToken(final String inputToken, final List<Character> inputCharacters, final List<String> expectedResult){
        // input arguments

        // mocks

        // expected results

        // creating test instance

        // execution test
        final List<String> actualResult = AbstractSentenceSplitter.prepareToken(inputToken, inputCharacters);

        // result assert
        assertEquals(expectedResult, actualResult);

        // mocks verify
    }

}
