package com.aif.language.sentence.separators.groupers;

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
        final ISentenceSeparatorsGrouper sentenceSeparatorsGrouper = ISentenceSeparatorsGrouper.Type.PREDEFINED.getInstance();

        // execution test
        final Map<ISentenceSeparatorsGrouper.Group, Set<Character>> actualResult = sentenceSeparatorsGrouper.group(inputTokens, inputSplitters);

        // result assert
        assertEquals(actualResult.get(ISentenceSeparatorsGrouper.Group.GROUP_1), expectedGroup1);
        assertEquals(actualResult.get(ISentenceSeparatorsGrouper.Group.GROUP_2), expectedGroup2);

        // mocks verify
    }
}