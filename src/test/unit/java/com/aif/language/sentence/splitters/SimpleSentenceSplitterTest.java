package com.aif.language.sentence.splitters;

import com.aif.language.common.ISplitter;
import com.aif.language.sentence.separators.clasificators.ISentenceSeparatorGroupsClassificatory;
import com.aif.language.sentence.separators.extractors.ISentenceSeparatorExtractor;
import com.aif.language.sentence.separators.groupers.ISentenceSeparatorsGrouper;
//import com.aif.language.sentence.splitters.SentenceSplitter;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class SimpleSentenceSplitterTest {

    @Test(groups = "unit-tests")
    public void testSplit() throws Exception {
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
        final ISentenceSeparatorExtractor mockSentenceSeparatorExtractor = mock(ISentenceSeparatorExtractor.class);
        when(mockSentenceSeparatorExtractor.extract(eq(inputTokens))).thenReturn(Optional.of(inputCharacters));

        final List<Set<Character>> mockGroups = new ArrayList<>();
        final Set<Character> mockGroup1 = new HashSet<>();
        mockGroup1.add('(');
        mockGroup1.add('!');
        mockGroup1.add('.');
        mockGroups.add(mockGroup1);
        mockGroups.add(Collections.emptySet());
        final ISentenceSeparatorsGrouper mockSentenceSeparatorsGrouper = mock(ISentenceSeparatorsGrouper.class);
        when(mockSentenceSeparatorsGrouper.group(inputTokens, inputCharacters)).thenReturn(mockGroups);

        final Map<ISentenceSeparatorGroupsClassificatory.Group, Set<Character>> classifiedGroups = new HashMap<>();
        classifiedGroups.put(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1, mockGroup1);
        classifiedGroups.put(ISentenceSeparatorGroupsClassificatory.Group.GROUP_2, Collections.emptySet());
        final ISentenceSeparatorGroupsClassificatory mockSentenceSeparatorGroupsClassificatory = mock(ISentenceSeparatorGroupsClassificatory.class);
        final Map<ISentenceSeparatorGroupsClassificatory.Group, Set<Character>> classGroups = new HashMap<>();
        classGroups.put(ISentenceSeparatorGroupsClassificatory.Group.GROUP_1, mockGroup1);
        classGroups.put(ISentenceSeparatorGroupsClassificatory.Group.GROUP_2, Collections.emptySet());
        when(mockSentenceSeparatorGroupsClassificatory.classify(inputTokens, mockGroups)).thenReturn(classGroups);

        // expected results
        final List<List<String>> expectedResult = new ArrayList<List<String>>() {
            {
                add(Arrays.asList(new String[]{"token", "..."}));
                add(Arrays.asList(new String[]{"(", "token"}));
                add(Arrays.asList(new String[]{"(!.", "token", ".."}));
                add(Arrays.asList(new String[]{"token", "tok!en"}));
            }
        };

        // creating test instance
//        final ISplitter<List<String>, List<String>> testInstance = new SentenceSplitter(
//                mockSentenceSeparatorExtractor,
//                mockSentenceSeparatorsGrouper,
//                mockSentenceSeparatorGroupsClassificatory);
//
//        // execution test
//        final List<List<String>> actualResult = testInstance.split(inputTokens);
//
//        // result assert
//        assertEquals(actualResult, expectedResult);
//
//        // mocks verify
//        verify(mockSentenceSeparatorExtractor, times(1)).extract(eq(inputTokens));
//        verify(mockSentenceSeparatorsGrouper, times(1)).group(inputTokens, inputCharacters);
//        verify(mockSentenceSeparatorGroupsClassificatory, times(1)).classify(inputTokens, mockGroups);
//        verifyNoMoreInteractions(mockSentenceSeparatorExtractor);
//        verifyNoMoreInteractions(mockSentenceSeparatorsGrouper);
//        verifyNoMoreInteractions(mockSentenceSeparatorGroupsClassificatory);
    }

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
//        final List<String> actualResults = SentenceSplitter.prepareSentences(inputTokens, inputCharacters);
//
//        // result assert
//        assertEquals(actualResults, expectedResults);
//
//        // mocks verify
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
//        final int actualResult = SentenceSplitter.lastNonSeparatorPosition(inputToken, inputCharacters);
//
//        // result assert
//        assertEquals(expectedResult, actualResult);
//
//        // mocks verify

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
//        final int actualResult = SentenceSplitter.firstNonSeparatorPosition(inputToken, inputCharacters);
//
//        // result assert
//        assertEquals(expectedResult, actualResult);
//
//        // mocks verify

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

//        // execution test
//        final int actualResult = SentenceSplitter.firstNonSeparatorPosition(inputToken, inputCharacters);
//
//        // result assert
//        assertEquals(actualResult, expectedResult);
//
//        // mocks verify

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
//        final int actualResult = SentenceSplitter.lastNonSeparatorPosition(inputToken, inputCharacters);
//
//        // result assert
//        assertEquals(expectedResult, actualResult);
//
//        // mocks verify

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
        final Set<Character> inputCharacters = new HashSet<Character>(){{
                add('.');
                add(')');
                add('(');
                add('!');
        }};

        // mocks

        // expected results
        final List<Boolean> expectedResult = Arrays.asList(new Boolean[]{
            false, false, true, true, true, false
        });

        // creating test instance

        // execution test
//        final List<Boolean> actualResult = SentenceSplitter.mapToBooleans(inputTokens, inputCharacters);
//
//        // result assert
//        assertEquals(expectedResult, actualResult);
//
//        // mocks verify
    }

    private static void testPrepareToken(final String inputToken, final List<Character> inputCharacters, final List<String> expectedResult){
        // input arguments

        // mocks

        // expected results

        // creating test instance

        // execution test
//        final List<String> actualResult = SentenceSplitter.prepareToken(inputToken, inputCharacters);
//
//        // result assert
//        assertEquals(expectedResult, actualResult);
//
//        // mocks verify
    }

}