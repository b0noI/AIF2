package io.aif.language.fact;

import io.aif.language.ner.NERExtractor;
import io.aif.language.ner.Type;
import io.aif.language.word.IWord;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

public class FactrTest {

    @Test(groups = "unit-tests")
    public void testGetProperNouns() throws Exception {
        final NERExtractor nerExtractor = mock(NERExtractor.class);

        final IWord word1 = mock(IWord.class);
        final IWord word2 = mock(IWord.class);

        when(nerExtractor.getNerType(word1)).thenReturn(Optional.of(Type.NOUN));
        when(nerExtractor.getNerType(word2)).thenReturn(Optional.empty());

        final Set<IWord> expected = new HashSet<>(Arrays.asList(word1));

        final List<IWord> semanticSentenceInput = Arrays.asList(word1, word2);

        final Factr testInstance = new Factr(null, nerExtractor);

        final IFact actual = testInstance.mapToFact(semanticSentenceInput);

        assertEquals(actual.getNamedEntities(), expected);
        assertEquals(actual.getSemanticSentence(), semanticSentenceInput);
    }

    @Test(groups = "unit-tests")
    public void testHasProperNoun() throws Exception {
        final NERExtractor nerExtractor = mock(NERExtractor.class);

        final IWord word1 = mock(IWord.class);
        final IWord word2 = mock(IWord.class);

        when(nerExtractor.getNerType(word1)).thenReturn(Optional.of(Type.NOUN));
        when(nerExtractor.getNerType(word2)).thenReturn(Optional.empty());

        boolean expected = true;

        final List<IWord> semanticSentenceInput = Arrays.asList(word1, word2);

        final Factr testInstance = new Factr(null, nerExtractor);

        final IFact actualFact = testInstance.mapToFact(semanticSentenceInput);

        final boolean actual = actualFact.hasNamedEntity(word1);

        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testHasProperNounFalse() throws Exception {
        final NERExtractor nerExtractor = mock(NERExtractor.class);

        final IWord word1 = mock(IWord.class);
        final IWord word2 = mock(IWord.class);
        final IWord word3 = mock(IWord.class);

        when(nerExtractor.getNerType(word1)).thenReturn(Optional.of(Type.NOUN));
        when(nerExtractor.getNerType(word2)).thenReturn(Optional.empty());
        when(nerExtractor.getNerType(word3)).thenReturn(Optional.empty());

        final boolean expected = false;

        final List<IWord> semanticSentenceInput = Arrays.asList(word1, word2);

        final Factr testInstance = new Factr(null, nerExtractor);
        final IFact actualFact = testInstance.mapToFact(semanticSentenceInput);

        final boolean actual = actualFact.hasNamedEntity(word3);

        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testHasProperNouns() throws Exception {
        final NERExtractor nerExtractor = mock(NERExtractor.class);

        final IWord word1 = mock(IWord.class);
        final IWord word2 = mock(IWord.class);
        final IWord word3 = mock(IWord.class);

        when(nerExtractor.getNerType(word1)).thenReturn(Optional.of(Type.NOUN));
        when(nerExtractor.getNerType(word2)).thenReturn(Optional.empty());
        when(nerExtractor.getNerType(word3)).thenReturn(Optional.of(Type.NOUN));

        final boolean expected = true;

        final List<IWord> semanticSentenceInput = Arrays.asList(word1, word2, word3);

        final Factr testInstance = new Factr(null, nerExtractor);
        final IFact actualFact = testInstance.mapToFact(semanticSentenceInput);

        final boolean actual = actualFact.hasNamedEntity(word3);

        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testHasProperNounsFalse() throws Exception {
        final NERExtractor nerExtractor = mock(NERExtractor.class);

        final IWord word1 = mock(IWord.class);
        final IWord word2 = mock(IWord.class);
        final IWord word3 = mock(IWord.class);
        final IWord word4 = mock(IWord.class);

        when(nerExtractor.getNerType(word1)).thenReturn(Optional.of(Type.NOUN));
        when(nerExtractor.getNerType(word2)).thenReturn(Optional.empty());
        when(nerExtractor.getNerType(word3)).thenReturn(Optional.empty());
        when(nerExtractor.getNerType(word4)).thenReturn(Optional.empty());

        final boolean expected = false;

        final List<IWord> semanticSentenceInput = Arrays.asList(word1, word2, word3, word4);

        final Factr testInstance = new Factr(null, nerExtractor);
        final IFact actualFact = testInstance.mapToFact(semanticSentenceInput);

        final boolean actual = actualFact.hasNamedEntity(word4);

        assertEquals(actual, expected);
    }

}
