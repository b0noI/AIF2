package io.aif.language.fact;

import io.aif.language.common.FuzzyBoolean;
import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class FactDefinerTest {

//    @Test(groups = "unit-tests")
//    public void testIsFact() throws Exception {
//        ISemanticNode<IWord> mockNode1 = mock(ISemanticNode.class);
//        when(mockNode1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//        ISemanticNode<IWord> mockNode2 = mock(ISemanticNode.class);
//        when(mockNode2.isProperNoun()).thenReturn(new FuzzyBoolean(.1, .2));
//        ISemanticNode<IWord> mockNode3 = mock(ISemanticNode.class);
//        when(mockNode3.isProperNoun()).thenReturn(new FuzzyBoolean(.1, .2));
//
//        boolean expected = true;
//
//        List<ISemanticNode<IWord>> semanticSentence = Arrays.asList(mockNode1, mockNode2, mockNode3);
//        IFactDefiner definer = new FactDefiner();
//        boolean actual = definer.isFact(semanticSentence);
//        assertEquals(actual, expected);
//    }
//
//    @Test(groups = "unit-tests")
//    public void testIsFactIfSentenceHasTwoProperNouns() throws Exception {
//        ISemanticNode<IWord> mockNode1 = mock(ISemanticNode.class);
//        when(mockNode1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//        ISemanticNode<IWord> mockNode2 = mock(ISemanticNode.class);
//        when(mockNode2.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//        ISemanticNode<IWord> mockNode3 = mock(ISemanticNode.class);
//        when(mockNode3.isProperNoun()).thenReturn(new FuzzyBoolean(.1, .2));
//
//        boolean expected = true;
//
//        List<ISemanticNode<IWord>> semanticSentence = Arrays.asList(mockNode1, mockNode2, mockNode3);
//        IFactDefiner definer = new FactDefiner(2);
//        boolean actual = definer.isFact(semanticSentence);
//        assertEquals(actual, expected);
//    }
//
//    @Test(groups = "unit-tests")
//    public void testIsFactFalse() throws Exception {
//        ISemanticNode<IWord> mockNode1 = mock(ISemanticNode.class);
//        when(mockNode1.isProperNoun()).thenReturn(new FuzzyBoolean(.1, .2));
//        ISemanticNode<IWord> mockNode2 = mock(ISemanticNode.class);
//        when(mockNode2.isProperNoun()).thenReturn(new FuzzyBoolean(.1, .2));
//        ISemanticNode<IWord> mockNode3 = mock(ISemanticNode.class);
//        when(mockNode3.isProperNoun()).thenReturn(new FuzzyBoolean(.1, .2));
//
//        boolean expected = false;
//
//        List<ISemanticNode<IWord>> semanticSentence = Arrays.asList(mockNode1, mockNode2, mockNode3);
//        IFactDefiner definer = new FactDefiner();
//        boolean actual = definer.isFact(semanticSentence);
//        assertEquals(actual, expected);
//    }
//
//    @Test(groups = "unit-tests")
//    public void testIsFactFalseWhenRequiredMoreThanOneProperNoun() throws Exception {
//        ISemanticNode<IWord> mockNode1 = mock(ISemanticNode.class);
//        when(mockNode1.isProperNoun()).thenReturn(new FuzzyBoolean(.2, .1));
//        ISemanticNode<IWord> mockNode2 = mock(ISemanticNode.class);
//        when(mockNode2.isProperNoun()).thenReturn(new FuzzyBoolean(.1, .2));
//        ISemanticNode<IWord> mockNode3 = mock(ISemanticNode.class);
//        when(mockNode3.isProperNoun()).thenReturn(new FuzzyBoolean(.1, .2));
//
//        boolean expected = false;
//
//        List<ISemanticNode<IWord>> semanticSentence = Arrays.asList(mockNode1, mockNode2, mockNode3);
//        IFactDefiner definer = new FactDefiner(2);
//        boolean actual = definer.isFact(semanticSentence);
//        assertEquals(actual, expected);
//    }
}