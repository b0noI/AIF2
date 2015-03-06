package io.aif.language.semantic;

import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassifier;
import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class RawGraphBuilderTest {

    /*
    @Test(groups = "unit-tests")
    public void testConstructionWithLookAheadValue() throws Exception {
        RawGraphBuilder builder = new RawGraphBuilder(100);
        assert true;
    }*/

    /*@Test(groups = "unit-tests", expectedExceptions = IllegalArgumentException.class)
    public void testConstructionWithInvalidLookAheadValue() throws Exception {
        RawGraphBuilder builder = new RawGraphBuilder(-2);
        assert false;
    }*/

    @Test(groups = "unit-tests")
    public void testBuild() throws Exception {
        List<String> tokens = Arrays.asList("I", "am", "a");

        Map<String, IWord> tokensToWords = new HashMap<>();
        tokens.forEach(token ->  {
            IWord iword = mock(IWord.class);
            when(iword.getRootToken()).thenReturn(token);
            when(iword.toString()).thenReturn(token);
            tokensToWords.putIfAbsent(token, iword);
        });
        assertEquals(tokensToWords.size(), 3);

        Map<String, IWord.IWordPlaceholder> tokensToPlaceHolders = new HashMap<>();
        tokens.forEach(token -> {
            IWord.IWordPlaceholder placeholder = mock(IWord.IWordPlaceholder.class);

            when(placeholder.getToken()).thenReturn(token);
            when(placeholder.getWord()).thenReturn(tokensToWords.get(token));

            tokensToPlaceHolders.put(token, placeholder);
        });
        Collection<IWord.IWordPlaceholder> input = tokensToPlaceHolders.values();

        Map<IWord, Map<IWord, List<Double>>> expected = new HashMap<>();
        expected.put(tokensToWords.get("I"), new HashMap<IWord, List<Double>>() {{
            put(tokensToWords.get("am"), Arrays.asList(1.0));
        }});
        expected.put(tokensToWords.get("am"), new HashMap<IWord, List<Double>>() {{
            put(tokensToWords.get("a"), Arrays.asList(1.0));
        }});
        expected.put(tokensToWords.get("a"), new HashMap<>());

        Map<ISeparatorGroupsClassifier.Group, Set<Character>> seperators = new HashMap<>();
        Set<Character> mockSeperatorGroup = mock(Set.class);
        when(mockSeperatorGroup.contains(any())).thenReturn(false);
        seperators.put(ISeparatorGroupsClassifier.Group.GROUP_1, mockSeperatorGroup);
        seperators.put(ISeparatorGroupsClassifier.Group.GROUP_2, mockSeperatorGroup);

        RawGraphBuilder builder = new RawGraphBuilder(1, seperators);
        Map<IWord, Map<IWord, List<Double>>> actual = builder.build(input);
        assertEquals(actual.keySet(), expected.keySet());
    }
}