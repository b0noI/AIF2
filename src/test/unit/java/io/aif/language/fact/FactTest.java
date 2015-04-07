package io.aif.language.fact;

import io.aif.language.common.FuzzyBoolean;
import io.aif.language.semantic.ISemanticNode;
import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class FactTest {

    @Test(groups = "unit-tests")
    public void testFactConstruction() throws Exception {
        IFact fact = new Fact(null);
        assert true;
    }

    @Test(groups = "unit-tests")
    public void testGetSemanticSentence() throws Exception {
        ISemanticNode<IWord> semanticNodeMock1 = mock(ISemanticNode.class);
        ISemanticNode<IWord> semanticNodeMock2 = mock(ISemanticNode.class);
        List<ISemanticNode<IWord>> semanticSentenceInput = Arrays.asList(semanticNodeMock1, semanticNodeMock2);

        IFact fact = new Fact(semanticSentenceInput);
        assertEquals(fact.getSemanticSentence(), semanticSentenceInput);
    }

    @Test(groups = "unit-tests")
    public void testGetProperNouns() throws Exception {
        ISemanticNode<IWord> semanticNodeMock1 = mock(ISemanticNode.class);
        when(semanticNodeMock1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
        ISemanticNode<IWord> semanticNodeMock2 = mock(ISemanticNode.class);
        when(semanticNodeMock2.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .4));

        Set<ISemanticNode<IWord>> expected = new HashSet<>(Arrays.asList(semanticNodeMock1));

        List<ISemanticNode<IWord>> semanticSentenceInput = Arrays.asList(semanticNodeMock1, semanticNodeMock2);
        IFact fact = new Fact(semanticSentenceInput);
        Set<ISemanticNode<IWord>> actual = fact.getProperNouns();

        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testHasProperNoun() throws Exception {
        ISemanticNode<IWord> semanticNodeMock1 = mock(ISemanticNode.class);
        when(semanticNodeMock1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
        ISemanticNode<IWord> semanticNodeMock2 = mock(ISemanticNode.class);
        when(semanticNodeMock2.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .4));

        boolean expected = true;

        List<ISemanticNode<IWord>> semanticSentenceInput = Arrays.asList(semanticNodeMock1, semanticNodeMock2);
        IFact fact = new Fact(semanticSentenceInput);
        boolean actual = fact.hasProperNoun(semanticNodeMock1);

        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testHasProperNounFalse() throws Exception {
        ISemanticNode<IWord> semanticNodeMock1 = mock(ISemanticNode.class);
        when(semanticNodeMock1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
        ISemanticNode<IWord> semanticNodeMock2 = mock(ISemanticNode.class);
        when(semanticNodeMock2.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .4));
        ISemanticNode<IWord> semanticNodeMock3 = mock(ISemanticNode.class);
        when(semanticNodeMock3.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));

        boolean expected = false;

        List<ISemanticNode<IWord>> semanticSentenceInput = Arrays.asList(semanticNodeMock1, semanticNodeMock2);
        IFact fact = new Fact(semanticSentenceInput);
        boolean actual = fact.hasProperNoun(semanticNodeMock3);

        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testHasProperNouns() throws Exception {
        ISemanticNode<IWord> semanticNodeMock1 = mock(ISemanticNode.class);
        when(semanticNodeMock1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
        ISemanticNode<IWord> semanticNodeMock2 = mock(ISemanticNode.class);
        when(semanticNodeMock2.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .4));
        ISemanticNode<IWord> semanticNodeMock3 = mock(ISemanticNode.class);
        when(semanticNodeMock3.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));

        boolean expected = true;

        List<ISemanticNode<IWord>> semanticSentenceInput = Arrays.asList(
                semanticNodeMock1, semanticNodeMock2, semanticNodeMock3);
        IFact fact = new Fact(semanticSentenceInput);
        boolean actual = fact.hasProperNouns(new HashSet(Arrays.asList(semanticNodeMock1, semanticNodeMock3)));

        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testHasProperNounsFalse() throws Exception {
        ISemanticNode<IWord> semanticNodeMock1 = mock(ISemanticNode.class);
        when(semanticNodeMock1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
        ISemanticNode<IWord> semanticNodeMock2 = mock(ISemanticNode.class);
        when(semanticNodeMock2.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .4));
        ISemanticNode<IWord> semanticNodeMock3 = mock(ISemanticNode.class);
        when(semanticNodeMock3.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
        ISemanticNode<IWord> semanticNodeMock4 = mock(ISemanticNode.class);
        when(semanticNodeMock4.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));

        boolean expected = false;

        List<ISemanticNode<IWord>> semanticSentenceInput = Arrays.asList(
                semanticNodeMock1, semanticNodeMock2, semanticNodeMock3);
        IFact fact = new Fact(semanticSentenceInput);
        boolean actual = fact.hasProperNouns(new HashSet(Arrays.asList(semanticNodeMock4)));

        assertEquals(actual, expected);
    }
}