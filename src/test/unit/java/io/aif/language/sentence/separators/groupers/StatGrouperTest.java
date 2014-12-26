package io.aif.language.sentence.separators.groupers;

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

    @Test(groups = "unit-tests", enabled = false)
    public void testParsGroup() throws Exception {
        // input arguments
        final Map<Character, Map<Character, Integer>> inputConnections = new HashMap<>();
        final Map<Character, Integer> dotConnections = new HashMap<>();
        dotConnections.put('A', 2);
        inputConnections.put('.', dotConnections);

        // mocks

        // expected results

        // creating test instance
        final StatGrouper testInstance = new StatGrouper(){

            @Override
            List<CharactersGroup> parsGroup(final Map<Character, Map<Character, Integer>> connections, final double limit) {
                assertEquals(connections, inputConnections);
                assertTrue(limit == .4 || limit == .2 || limit == .1 || limit == 0.15000000000000002);
                final List<CharactersGroup> result = mock(List.class);
                if (limit == .4) {
                    when(result.size()).thenReturn(3);
                } else if (limit == .2) {
                    when(result.size()).thenReturn(2);
                } else if (limit == .1) {
                    when(result.size()).thenReturn(1);
                } else {
                    when(result.size()).thenReturn(1);
                }
                return result;
            }

        };

        // execution test
        final List<StatGrouper.CharactersGroup> actualResult = testInstance.parsGroup(inputConnections);

        // result assert
        assertEquals(actualResult.size(), 2);

        // mocks verify
    }

    @Test(groups = "unit-tests")
    public void testParsGroupInnerWhenExpectingZeroGroups() throws Exception {
        // input arguments
        final Map<Character, Map<Character, Integer>> inputConnections = new HashMap<>();
        final Map<Character, Integer> dotConnections = new HashMap<>();
        dotConnections.put('A', 2);
        inputConnections.put('.', dotConnections);

        // mocks

        // expected results

        // creating test instance
        final StatGrouper testInstance = new StatGrouper(){

            @Override
            Map<Character, Double> convertConnections(Map<Character, Integer> connections) {
                fail();
                return null;
            }

            @Override
            void addCharactersToGroup(Map<Character, Double> characters, Character root, List<CharactersGroup> groups, double limit) {
                fail();
            }

        };

        // execution test
        final List<StatGrouper.CharactersGroup> actualResult = testInstance.parsGroup(inputConnections, 0.5);

        // result assert
        assertEquals(actualResult.size(), 0);

        // mocks verify
    }

    @Test(groups = "unit-tests")
    public void testParsGroupInnerWhenExpectingOneGroup() throws Exception {
        // input arguments
        final Map<Character, Map<Character, Integer>> inputConnections = new HashMap<>();
        final Map<Character, Integer> dotConnections = new HashMap<>();
        dotConnections.put('A', 2);
        dotConnections.put('B', 2);
        dotConnections.put('C', 2);
        dotConnections.put('D', 2);
        inputConnections.put('.', dotConnections);

        // mocks
        final Map<Character, Double> convertedConnections = mock(Map.class);
        final StatGrouper.CharactersGroup mockGroup = mock(StatGrouper.CharactersGroup.class);

        // expected results

        // creating test instance
        final StatGrouper testInstance = new StatGrouper(){

            @Override
            Map<Character, Double> convertConnections(Map<Character, Integer> connections) {
                assertEquals(connections, dotConnections);
                return convertedConnections;
            }

            @Override
            void addCharactersToGroup(Map<Character, Double> characters, Character root, List<CharactersGroup> groups, double limit) {
                assertEquals(characters, convertedConnections);
                assertEquals(root, new Character('.'));
                assertEquals(groups.size(), 0);
                assertEquals(limit, .3);
                groups.add(mockGroup);
            }

        };

        // execution test
        final List<StatGrouper.CharactersGroup> actualResult = testInstance.parsGroup(inputConnections, 0.3);

        // result assert
        assertEquals(actualResult.size(), 1);
        assertTrue(actualResult.get(0) == mockGroup);

        // mocks verify
    }

    @Test(groups = "unit-tests")
    public void testAddCharactersToGroup() throws Exception {
        // input arguments
        final Map<Character, Double> inputCharacters = new HashMap<>();
        inputCharacters.put('A', .2);
        inputCharacters.put('B', .3);
        inputCharacters.put('C', .8);
        inputCharacters.put('D', 1.);
        final Character inputRoot  = '.';
        final List<StatGrouper.CharactersGroup> groups = new ArrayList<>();
        final double limit = 0.3;

        // mocks

        // expected results

        // creating test instance
        final StatGrouper testInstance = new StatGrouper();

        // execution test
        testInstance.addCharactersToGroup(inputCharacters, inputRoot, groups, limit);

        // result assert
        assertEquals(groups.size(), 1);
        assertEquals(groups.get(0).getSplitters().size(), 1);
        assertEquals(groups.get(0).getSplitters().toArray()[0], '.');

        // mocks verify
    }

    @Test(groups = "unit-tests")
    public void testAddCharactersToGroupWhenDifferentGroupExists() throws Exception {
        // input arguments
        final Map<Character, Double> inputCharacters = new HashMap<>();
        inputCharacters.put('A', .2);
        inputCharacters.put('B', .3);
        inputCharacters.put('C', .8);
        inputCharacters.put('D', 1.);
        final Character inputRoot  = '.';
        final List<StatGrouper.CharactersGroup> groups = new ArrayList<>();

        final Map<Character, Double> commaCharacters = new HashMap<>();
        commaCharacters.put('a', .2);
        commaCharacters.put('b', .3);
        commaCharacters.put('c', .8);
        commaCharacters.put('d', 1.);
        final StatGrouper.CharactersGroup commaGroup = new StatGrouper.CharactersGroup(commaCharacters, ',');
        groups.add(commaGroup);

        final double limit = 0.3;

        // mocks

        // expected results

        // creating test instance
        final StatGrouper testInstance = new StatGrouper();

        // execution test
        testInstance.addCharactersToGroup(inputCharacters, inputRoot, groups, limit);

        // result assert
        assertEquals(groups.size(), 2);

        // mocks verify
    }

    @Test(groups = "unit-tests")
    public void testAddCharactersToGroupWhenSameGroupExists() throws Exception {
        // input arguments
        final Map<Character, Double> inputCharacters = new HashMap<>();
        inputCharacters.put('A', .2);
        inputCharacters.put('B', .3);
        inputCharacters.put('C', .8);
        inputCharacters.put('D', 1.);
        final Character inputRoot  = '!';
        final List<StatGrouper.CharactersGroup> groups = new ArrayList<>();

        final Map<Character, Double> commaCharacters = new HashMap<>();
        inputCharacters.put('a', .2);
        inputCharacters.put('B', .3);
        inputCharacters.put('C', .8);
        inputCharacters.put('D', 1.);
        final StatGrouper.CharactersGroup commaGroup = new StatGrouper.CharactersGroup(commaCharacters, ',');
        groups.add(commaGroup);

        final double limit = 0.3;

        // mocks

        // expected results

        // creating test instance
        final StatGrouper testInstance = new StatGrouper();

        // execution test
        testInstance.addCharactersToGroup(inputCharacters, inputRoot, groups, limit);

        // result assert
        assertEquals(groups.size(), 1);
        assertEquals(groups.get(0).getSplitters().size(), 2);

        // mocks verify
    }

}