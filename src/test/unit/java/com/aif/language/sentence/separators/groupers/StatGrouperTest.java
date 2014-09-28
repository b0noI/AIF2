package com.aif.language.sentence.separators.groupers;

import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class StatGrouperTest {

    @Test(groups = "unit-tests")
    public void testGroup() throws Exception {
        // input arguments
        final List<String> inputTokens = Arrays.asList(new String[]{"token1", "token2", "token3"});
        final List<Character> inputCharacters = Arrays.asList(new Character[]{'.', ','});

        // mocks
        final List<String> filteredTokens = Arrays.asList(new String[]{"token1", "token2"});
        final Map<Character, Integer> dotMap = new HashMap<Character, Integer>(){{
            put('A', 2);
            put('B', 3);
        }};
        final Map<Character, Map<Character, Integer>> mockCharactersConnections = new HashMap<Character, Map<Character, Integer>>() {{
            put('.', dotMap);
        }};
        final StatGrouper.CharactersGroup mockCharactersGroup = mock(StatGrouper.CharactersGroup.class);

        // expected results
        final List<Set<Character>> expectedCharacters = new ArrayList<Set<Character>>(){{
            add(new HashSet<Character>(){{add('.');}});
        }};

        // creating test instance
        final StatGrouper testInstance = new StatGrouper() {

            @Override
            List<String> filterTokens(List<String> tokens) {
                assertEquals(tokens, inputTokens);
                return filteredTokens;
            }

            @Override
            Map<Character, Map<Character, Integer>> parsConnections(List<String> tokens, List<Character> splitters) {
                assertEquals(tokens, filteredTokens);
                assertEquals(splitters, inputCharacters);
                return mockCharactersConnections;
            }

            @Override
            void filterConnections(Map<Character, Map<Character, Integer>> connections) {
                assertEquals(connections, mockCharactersConnections);
            }

            @Override
            List<CharactersGroup> parsGroup(Map<Character, Map<Character, Integer>> connections) {
                assertEquals(connections, mockCharactersConnections);
                return new ArrayList<CharactersGroup>(){{add(mockCharactersGroup);}};
            }

            @Override
            List<Set<Character>> convert(List<CharactersGroup> groups) {
                assertEquals(groups, new ArrayList<CharactersGroup>(){{add(mockCharactersGroup);}});
                return expectedCharacters;
            }

        };

        // execution test
        final List<Set<Character>> actualResult = testInstance.group(inputTokens, inputCharacters);

        // result assert
        assertEquals(actualResult, expectedCharacters);

        // mocks verify
    }

    @Test(groups = "unit-tests")
    public void testConvert() throws Exception {
        // input arguments
        final Map<Character, Double> groupChracters = new HashMap<Character, Double>(){{put('.', 1.);}};
        final List<StatGrouper.CharactersGroup> inputCharactersGroups = new ArrayList<StatGrouper.CharactersGroup>(){{add(new StatGrouper.CharactersGroup(groupChracters, '.'));}};

        // mocks

        // expected results
        final List<Set<Character>> expectedCharacters = new ArrayList<Set<Character>>(){{
            add(new HashSet<Character>(){{add('.');}});
        }};

        // creating test instance
        final StatGrouper testInstance = new StatGrouper();

        // execution test
        final List<Set<Character>> actualResult = testInstance.convert(inputCharactersGroups);

        // result assert
        assertEquals(actualResult, expectedCharacters);

        // mocks verify
    }
}