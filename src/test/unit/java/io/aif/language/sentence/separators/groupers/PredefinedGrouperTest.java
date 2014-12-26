package io.aif.language.sentence.separators.groupers;

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

public class PredefinedGrouperTest {

    @Test(groups = "unit-tests")
    public void testGroup() throws Exception {
        // input arguments
        final List<Character> inputSplitters = Arrays.asList(new Character[]{
            '.', '\'', '#', '$', '!'
        });
        final List<String> inputTokens = null;

        // mocks

        // expected results
        final Set<Character> expectedGroup1 = new HashSet<Character>(){{
            add('.');
            add('!');
        }};
        final Set<Character> expectedGroup2 = new HashSet<Character>(){{
            add('\'');
            add('#');
            add('$');
        }};

        // creating test instance
        final ISeparatorsGrouper sentenceSeparatorsGrouper = ISeparatorsGrouper.Type.PREDEFINED.getInstance();

        // execution test
        final List<Set<Character>> actualResult = sentenceSeparatorsGrouper.group(inputTokens, inputSplitters);

        // result assert
        assertTrue(actualResult.size() == 2);
        if (actualResult.get(0).equals(expectedGroup1)) {
            assertEquals(actualResult.get(0), expectedGroup1);
            assertEquals(actualResult.get(1), expectedGroup2);
        } else {
            assertEquals(actualResult.get(1), expectedGroup1);
            assertEquals(actualResult.get(0), expectedGroup2);
        }

        // mocks verify
    }
}