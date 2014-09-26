package com.aif.language.sentence.separators.groupers;

import com.aif.language.sentence.separators.clasificators.ISentenceSeparatorGroupsClassificatory;
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
        final List<Set<Character>> actualResult = sentenceSeparatorsGrouper.group(inputTokens, inputSplitters);

        // result assert
        assertEquals(null, expectedGroup1);
        assertEquals(null, expectedGroup2);

        // mocks verify
    }
}