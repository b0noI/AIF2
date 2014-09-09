package com.aif.language.sentence;

import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class StatSentenceSeparatorExtractorTest {

    @Test(groups = "unit-tests")
    public void testExtract() throws Exception {
        // input arguments
        final List<String> inputTokens = Arrays.asList(new String[]{"token1", "token2"});

        // mocks
        final StatSentenceSeparatorExtractor.CharacterStat mockCharacterStat1 = mock(StatSentenceSeparatorExtractor.CharacterStat.class);
        final StatSentenceSeparatorExtractor.CharacterStat mockCharacterStat2 = mock(StatSentenceSeparatorExtractor.CharacterStat.class);
        final List<StatSentenceSeparatorExtractor.CharacterStat> mockCharacterStats = new ArrayList<>();
        mockCharacterStats.add(mockCharacterStat1);
        mockCharacterStats.add(mockCharacterStat2);

        when(mockCharacterStat1.getCharacter()).thenReturn('a');
        when(mockCharacterStat2.getCharacter()).thenReturn('b');

        // expected results
        final Optional<List<Character>> expectedResult = Optional.of(Arrays.asList(new Character[]{'a'}));

        // creating test instance
        final StatSentenceSeparatorExtractor testInstance = new StatSentenceSeparatorExtractor() {

            @Override
            List<CharacterStat> getCharactersStat(List<String> tokens) {
                assertEquals(tokens, inputTokens);
                return mockCharacterStats;
            }

            @Override
            List<CharacterStat> filterCharacterStatisticFromNonEndCharacters(List<CharacterStat> characterStats) {
                assertEquals(characterStats, mockCharacterStats);
                return mockCharacterStats.subList(0, 1);
            }

        };

        // execution test
        final Optional<List<Character>> actualResult =  testInstance.extract(inputTokens);

        // result assert
        assertEquals(actualResult, expectedResult);

        // mocks verify
        verify(mockCharacterStat1, times(1)).getCharacter();
        verify(mockCharacterStat2, times(0)).getCharacter();
    }

    @Test(groups = "unit-tests")
    public void testGetCharactersStat() throws Exception {
        // input arguments
        final List<String> inputArguments = Arrays.asList(new String[]{"token", "otkne"});

        // mocks
        final StatSentenceSeparatorExtractor.CharacterStat mockCharacterStat = mock(StatSentenceSeparatorExtractor.CharacterStat.class);
        final List<StatSentenceSeparatorExtractor.CharacterStat> mockCharacterStats = new ArrayList<>();
        mockCharacterStats.add(mockCharacterStat);

        // expected results
        final Set<Character> characterSet = new HashSet<Character>(){{
            add('t');
            add('o');
            add('k');
            add('e');
            add('n');
        }};

        // creating test instance
        final StatSentenceSeparatorExtractor testInstance = new StatSentenceSeparatorExtractor() {

            @Override
            List<String> filter(List<String> tokens) {
                assertEquals(tokens, inputArguments);
                return tokens;
            }

            @Override
            List<CharacterStat> getCharactersStatistic(StatData startCharacterStatData, StatData endCharactersStatData) {
                assertEquals(startCharacterStatData.getAllCharacters(), characterSet);
                assertEquals(endCharactersStatData.getAllCharacters(), characterSet);
                assertEquals(startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter('t'), 0.25);
                assertEquals(startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter('o'), 0.25);
                assertEquals(startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter('k'), 0.0);
                assertEquals(startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter('e'), 0.0);
                assertEquals(startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter('n'), 0.0);
                assertEquals(endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter('t'), 0.0);
                assertEquals(endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter('o'), 0.0);
                assertEquals(endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter('k'), 0.0);
                assertEquals(endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter('e'), 0.25);
                assertEquals(endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter('n'), 0.25);
                return mockCharacterStats;
            }

        };

        // execution test
        final List<StatSentenceSeparatorExtractor.CharacterStat> actualResult = testInstance.getCharactersStat(inputArguments);

        // result assert
        assertEquals(actualResult, mockCharacterStats);

        // mocks verify
    }

    @Test
    public void testFilterCharacterStatisticFromNonEndCharacters() throws Exception {
        // TODO
    }

    @Test
    public void testGetCharactersStatistic() throws Exception {
        // TODO
    }

    @Test(groups = "unit-tests")
    public void testFilter() throws Exception {
        // input arguments
        final List<String> inputTokens = Arrays.asList(new String[]{"tOKen", "token..."});

        // mocks

        // expected results
        final List<String> expectedResult = Arrays.asList(new String[]{"token", "token."});

        // creating test instance
        final StatSentenceSeparatorExtractor testInstance = new StatSentenceSeparatorExtractor();

        // execution test
        final List<String> actualResult = testInstance.filter(inputTokens);

        // result assert
        assertEquals(actualResult, expectedResult);

        // mocks verify
    }

}