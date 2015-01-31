package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class TokensCountBasedWeightCalculatorTest {

    @Test(groups = "unit-tests")
    public void testCalculateWeight() throws Exception {
        // input arguments
        final ISemanticNode<IWord> inputWord = mock(ISemanticNode.class);
        
        // mocks
        final IWord mockWord = mock(IWord.class);
        when(mockWord.getAllTokens()).thenReturn(
                new HashSet<String>(){{addAll(Arrays.asList(new String[]{"1", "2", "3", "4", "5"}));}}
        );
        when(inputWord.item()).thenReturn(mockWord);
        
        // expected results
        final double expectedResult = .8;
        
        // creating test instance
        final IWordWeightCalculator testInstance = new TokensCountBasedWeightCalculator();
        
        // execution test
        final double actualResult = testInstance.calculateWeight(inputWord);
        
        // result assert
        assertEquals(actualResult, expectedResult);
        
        // mocks verify
        verify(inputWord, times(1)).item();
        verify(mockWord, times(1)).getAllTokens();
    }
    
}