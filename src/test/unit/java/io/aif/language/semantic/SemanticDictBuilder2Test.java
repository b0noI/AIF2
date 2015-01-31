package io.aif.language.semantic;

import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class SemanticDictBuilder2Test {

    @Test(groups = "unit-tests")
    public void testConstructionWithoutArguments() throws Exception {
        SemanticDictBuilder2 builder = new SemanticDictBuilder2();
        assert true;
    }

    @Test(groups = "unit-tests")
    public void testConstructionWithLookAheadValue() throws Exception {
        SemanticDictBuilder2 builder = new SemanticDictBuilder2(100);
        assert true;
    }

    @Test(groups = "unit-tests", expectedExceptions = IllegalArgumentException.class)
    public void testConstructionWithInvalidLookAheadValue() throws Exception {
        SemanticDictBuilder2 builder = new SemanticDictBuilder2(-2);
        assert false;
    }

    @Test(groups = "unit-tests")
    public void testBuild() throws Exception {
        List<String> tokens = Arrays.asList("I", "am", "a", "a");

        Map<String, IWord> tokensToWords = new HashMap<>();
        tokens.forEach(token ->  {
            IWord iword = mock(IWord.class);
            when(iword.getRootToken()).thenReturn(token);
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

        Map<String, SemanticWord> tokensToSemanticWords = new HashMap<>();
        tokens.forEach(token -> {
            IWord iword = tokensToWords.get(token);
            tokensToSemanticWords.put(token, new SemanticWord(iword));
        });

        tokensToSemanticWords.get("I").addEdge(tokensToSemanticWords.get("am"), 1);
        tokensToSemanticWords.get("I").addEdge(tokensToSemanticWords.get("a"), 2);
        tokensToSemanticWords.get("I").addEdge(tokensToSemanticWords.get("a"), 3);

        tokensToSemanticWords.get("am").addEdge(tokensToSemanticWords.get("a"), 1);
        tokensToSemanticWords.get("am").addEdge(tokensToSemanticWords.get("a"), 2);

        //TODO: Do we connect a word to itself.
        tokensToSemanticWords.get("a").addEdge(tokensToSemanticWords.get("a"), 1);

        Set<ISemanticNode<IWord>> expectedSemanticWords = new HashSet<>(tokensToSemanticWords.values());
        Map<IWord, ISemanticNode<IWord>> iwordToISemanticWord = new HashMap<>();
        expectedSemanticWords.forEach(semanticWord -> {
            iwordToISemanticWord.put(semanticWord.item(), semanticWord);
        });

        SemanticDictBuilder2 builder = new SemanticDictBuilder2();
        ISemanticDict actual = builder.build(input);
        //TODO: This test is fragile.
        for (ISemanticNode<IWord> semanticWord : actual.getWords()) {
            IWord word = semanticWord.item();
            iwordToISemanticWord.get(word);
        }
    }
}