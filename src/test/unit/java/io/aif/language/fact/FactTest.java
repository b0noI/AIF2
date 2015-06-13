package io.aif.language.fact;

import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;

public class FactTest {

    @Test(groups = "unit-tests")
    public void testFactConstruction() throws Exception {
        final IFact fact = new Fact(Collections.emptyList());
        assert true;
    }

    @Test(groups = "unit-tests")
    public void testGetSemanticSentence() throws Exception {
        IWord semanticNodeMock1 = mock(IWord.class);
        IWord semanticNodeMock2 = mock(IWord.class);
        List<IWord> semanticSentenceInput = Arrays.asList(semanticNodeMock1, semanticNodeMock2);

        IFact fact = new Fact(semanticSentenceInput);
        assertEquals(fact.getSemanticSentence(), semanticSentenceInput);
    }

//    @Test(groups = "unit-tests")
//    public void testGetProperNouns() throws Exception {
//        ISemanticNode<IWord> semanticNodeMock1 = mock(ISemanticNode.class);
//        when(semanticNodeMock1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//        ISemanticNode<IWord> semanticNodeMock2 = mock(ISemanticNode.class);
//        when(semanticNodeMock2.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .4));
//
//        Set<ISemanticNode<IWord>> expected = new HashSet<>(Arrays.asList(semanticNodeMock1));
//
//        List<IWord> semanticSentenceInput = Arrays.asList(semanticNodeMock1, semanticNodeMock2);
//        IFact fact = new Fact(semanticSentenceInput);
//        Set<T> actual = fact.getNamedEntities();
//
//        assertEquals(actual, expected);
//    }
//
//    @Test(groups = "unit-tests")
//    public void testHasProperNoun() throws Exception {
//        ISemanticNode<IWord> semanticNodeMock1 = mock(ISemanticNode.class);
//        when(semanticNodeMock1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//        ISemanticNode<IWord> semanticNodeMock2 = mock(ISemanticNode.class);
//        when(semanticNodeMock2.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .4));
//
//        boolean expected = true;
//
//        List<IWord> semanticSentenceInput = Arrays.asList(semanticNodeMock1, semanticNodeMock2);
//        IFact fact = new Fact(semanticSentenceInput);
//        boolean actual = fact.hasNamedEntity(semanticNodeMock1);
//
//        assertEquals(actual, expected);
//    }
//
//    @Test(groups = "unit-tests")
//    public void testHasProperNounFalse() throws Exception {
//        ISemanticNode<IWord> semanticNodeMock1 = mock(ISemanticNode.class);
//        when(semanticNodeMock1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//        ISemanticNode<IWord> semanticNodeMock2 = mock(ISemanticNode.class);
//        when(semanticNodeMock2.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .4));
//        ISemanticNode<IWord> semanticNodeMock3 = mock(ISemanticNode.class);
//        when(semanticNodeMock3.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//
//        boolean expected = false;
//
//        List<IWord> semanticSentenceInput = Arrays.asList(semanticNodeMock1, semanticNodeMock2);
//        IFact fact = new Fact(semanticSentenceInput);
//        boolean actual = fact.hasNamedEntity(semanticNodeMock3);
//
//        assertEquals(actual, expected);
//    }
//
//    @Test(groups = "unit-tests")
//    public void testHasProperNouns() throws Exception {
//        ISemanticNode<IWord> semanticNodeMock1 = mock(ISemanticNode.class);
//        when(semanticNodeMock1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//        ISemanticNode<IWord> semanticNodeMock2 = mock(ISemanticNode.class);
//        when(semanticNodeMock2.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .4));
//        ISemanticNode<IWord> semanticNodeMock3 = mock(ISemanticNode.class);
//        when(semanticNodeMock3.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//
//        boolean expected = true;
//
//        List<IWord> semanticSentenceInput = Arrays.asList(
//                semanticNodeMock1, semanticNodeMock2, semanticNodeMock3);
//        IFact fact = new Fact(semanticSentenceInput);
//        boolean actual = fact.hasNamedEntities(new HashSet(Arrays.asList(semanticNodeMock1, semanticNodeMock3)));
//
//        assertEquals(actual, expected);
//    }
//
//    @Test(groups = "unit-tests")
//    public void testHasProperNounsFalse() throws Exception {
//        ISemanticNode<IWord> semanticNodeMock1 = mock(ISemanticNode.class);
//        when(semanticNodeMock1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//        ISemanticNode<IWord> semanticNodeMock2 = mock(ISemanticNode.class);
//        when(semanticNodeMock2.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .4));
//        ISemanticNode<IWord> semanticNodeMock3 = mock(ISemanticNode.class);
//        when(semanticNodeMock3.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//        ISemanticNode<IWord> semanticNodeMock4 = mock(ISemanticNode.class);
//        when(semanticNodeMock4.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//
//        boolean expected = false;
//
//        List<IWord> semanticSentenceInput = Arrays.asList(
//                semanticNodeMock1, semanticNodeMock2, semanticNodeMock3);
//        IFact fact = new Fact(semanticSentenceInput);
//        boolean actual = fact.hasNamedEntities(new HashSet(Arrays.asList(semanticNodeMock4)));
//
//        assertEquals(actual, expected);
//    }
}