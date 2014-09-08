package com.aif.language.sentence;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

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

    @Test
    public void testGetCharactersStat() throws Exception {
        // TODO
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

        // execution test
        final List<String> actualResult = StatSentenceSeparatorExtractor.filter(inputTokens);

        // result assert
        assertEquals(actualResult, expectedResult);

        // mocks verify
    }

}