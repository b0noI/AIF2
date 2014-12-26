package io.aif.language.sentence.separators.extractors;

import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class StatSentenceSeparatorExtractorTest {

    @Test(groups = "unit-tests")
    public void testExtract() throws Exception {
        // input arguments
        final List<String> inputTokens = Arrays.asList(new String[]{"token1", "token2"});

        // mocks

        // expected results
        final Optional<List<Character>> expectedResult = Optional.of(Arrays.asList(new Character[]{'a'}));

        // creating test instance
        final StatSeparatorExtractor testInstance = new StatSeparatorExtractor() {

            @Override
            List<Character> getCharacters(List<String> tokens) {
                assertEquals(tokens, inputTokens);
                return expectedResult.get();
            }

        };

        // execution test
        final Optional<List<Character>> actualResult =  testInstance.extract(inputTokens);

        // result assert
        assertEquals(actualResult, expectedResult);

        // mocks verify
    }

    @Test(groups = "unit-tests", enabled = false)
    public void testGetCharacters() throws Exception {
        // input arguments
        final List<String> inputArguments = Arrays.asList(new String[]{"token", "otkne"});

        // mocks
        final StatSeparatorExtractor.CharacterStat mockCharacterStat = mock(StatSeparatorExtractor.CharacterStat.class);
        final List<StatSeparatorExtractor.CharacterStat> mockCharacterStats = new ArrayList<>();
        mockCharacterStats.add(mockCharacterStat);

        // expected results
        final Set<Character> characterSet = new HashSet<Character>(){{
            add('t');
            add('o');
            add('k');
            add('e');
            add('n');
        }};
        final List<Character> expectedResult = new ArrayList<>(characterSet);

        // creating test instance
        final StatSeparatorExtractor testInstance = new StatSeparatorExtractor() {

            @Override
            List<String> filter(List<String> tokens) {
                assertEquals(tokens, inputArguments);
                return tokens;
            }

            @Override
            List<CharacterStat> getNormalizedCharactersStatistic(StatData startCharacterStatData, StatData endCharactersStatData) {
                assertEquals(startCharacterStatData.getAllCharacters(), characterSet);
                assertEquals(endCharactersStatData.getAllCharacters(), characterSet);
                assertEquals(startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter('t'), 0.125);
                assertEquals(startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter('o'), 0.125);
                assertEquals(startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter('k'), 0.0);
                assertEquals(startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter('e'), 0.0);
                assertEquals(startCharacterStatData.getProbabilityThatCharacterIsSplitterCharacter('n'), 0.0);
                assertEquals(endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter('t'), 0.0);
                assertEquals(endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter('o'), 0.0);
                assertEquals(endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter('k'), 0.0);
                assertEquals(endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter('e'), 0.125);
                assertEquals(endCharactersStatData.getProbabilityThatCharacterIsSplitterCharacter('n'), 0.125);
                return mockCharacterStats;
            }

            @Override
            List<Character> convertCharacterStatToCharacters(List<CharacterStat> charactersStats) {
                assertEquals(charactersStats, mockCharacterStats);
                return expectedResult;
            }

        };

        // execution test
        final List<Character> actualResult = testInstance.getCharacters(inputArguments);

        // result assert
        assertEquals(actualResult, expectedResult);

        // mocks verify
    }

//    @Test(groups = "unit-tests")
//    public void testFilterCharacterStatisticFromNonEndCharacters() throws Exception {
//        // input arguments
//        final StatSeparatorExtractor.CharacterStat stat1 = new StatSeparatorExtractor.CharacterStat('t', 0.1);
//        final StatSeparatorExtractor.CharacterStat stat2 = new StatSeparatorExtractor.CharacterStat('o', 0.2);
//        final List<StatSeparatorExtractor.CharacterStat> inputStats = new ArrayList<>();
//        inputStats.add(stat1);
//        inputStats.add(stat2);
//
//        // mocks
//        final SummaryStatistics mockSummaryStatistics = mock(SummaryStatistics.class);
//        when(mockSummaryStatistics.getMean()).thenReturn(0.1);
//        when(mockSummaryStatistics.getStandardDeviation()).thenReturn(0.05);
//
//
//        // expected results
//        final List<StatSeparatorExtractor.CharacterStat> expectedResult = new ArrayList<>();
//        expectedResult.add(stat2);
//
//        // creating test instance
//        final StatSeparatorExtractor testInstance = new StatSeparatorExtractor();
//
//        // execution test
//        final List<StatSeparatorExtractor.CharacterStat> actualResult = testInstance.firstFilter(inputStats);
//
//        // result assert
//        assertEquals(actualResult, expectedResult);
//
//    }

    @Test(groups = "unit-tests")
    public void testGetCharactersStatistic() throws Exception {
        // input arguments
        final StatData startCharacterStatData = new StatData();
        final StatData endCharacterStatData = new StatData();
        for (char ch : "token".toCharArray()) {
            startCharacterStatData.addCharacter(ch);
            endCharacterStatData.addCharacter(ch);
        }
        startCharacterStatData.addCharacter('e');
        startCharacterStatData.addCharacter('n');
        endCharacterStatData.addCharacter('e');
        endCharacterStatData.addCharacter('e');
        endCharacterStatData.addCharacter('n');
        endCharacterStatData.addCharacter('n');

        startCharacterStatData.addEdgeCharacter('e', 'n');
        startCharacterStatData.addEdgeCharacter('n', 'e');
        endCharacterStatData.addEdgeCharacter('e', 'n');
        endCharacterStatData.addEdgeCharacter('e', 'n');
        endCharacterStatData.addEdgeCharacter('n', 'e');

        // mocks

        // expected results
        final List<StatSeparatorExtractor.CharacterStat> expectedResult = new ArrayList<>();
        expectedResult.add(new StatSeparatorExtractor.CharacterStat('n', 0.25));
        expectedResult.add(new StatSeparatorExtractor.CharacterStat('e', 0.25));
        expectedResult.add(new StatSeparatorExtractor.CharacterStat('t', 0.));
        expectedResult.add(new StatSeparatorExtractor.CharacterStat('o', 0.));
        expectedResult.add(new StatSeparatorExtractor.CharacterStat('k', 0.));

        // creating test instance
        final StatSeparatorExtractor testInstance = new StatSeparatorExtractor();

        // execution test
        final List<StatSeparatorExtractor.CharacterStat> actualResult = testInstance.getNormalizedCharactersStatistic(startCharacterStatData, endCharacterStatData);

        // result assert
        actualResult.forEach(result -> expectedResult.contains(result));

        // mocks verify
    }

    @Test(groups = "unit-tests")
    public void testFilter() throws Exception {
        // input arguments
        final List<String> inputTokens = Arrays.asList(new String[]{"tOKen", "token...", "tok", "0"});

        // mocks

        // expected results
        final List<String> expectedResult = Arrays.asList(new String[]{"token", "token."});

        // creating test instance
        final StatSeparatorExtractor testInstance = new StatSeparatorExtractor();

        // execution test
        final List<String> actualResult = testInstance.filter(inputTokens);

        // result assert
        assertEquals(actualResult, expectedResult);

        // mocks verify
    }

}