package io.aif.language.semantic.weights.node.word;

import io.aif.language.semantic.ISemanticNode;
import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class CompositeNodeWeightCalculatorTest {

    @Test(groups = "unit-tests", enabled = false)
    public void testCalculateWeight() throws Exception {
        // input arguments
        final Set<INodeWeightCalculator<String>> inputCalculators = new HashSet<>();
        final String node = mock(String.class);
        
        // mocks
        final INodeWeightCalculator<String> nodeWeightCalculator1 = mock(INodeWeightCalculator.class);
        final INodeWeightCalculator<String> nodeWeightCalculator2 = mock(INodeWeightCalculator.class);
        final INodeWeightCalculator<String> nodeWeightCalculator3 = mock(INodeWeightCalculator.class);
        
        when(nodeWeightCalculator1.calculateWeight(node)).thenReturn(.1);
        when(nodeWeightCalculator2.calculateWeight(node)).thenReturn(.2);
        when(nodeWeightCalculator3.calculateWeight(node)).thenReturn(.3);

        inputCalculators.add(nodeWeightCalculator1);
        inputCalculators.add(nodeWeightCalculator2);
        inputCalculators.add(nodeWeightCalculator3);
        
        // expected results
        final double expectedResult = 0.19999999999999998;
        
        // creating test instance
        final INodeWeightCalculator<String> testInstance = new CompositeNodeWeightCalculator<>(inputCalculators);
        
        // execution test
        final double actualResult = testInstance.calculateWeight(node);
        
        // result assert
        assertEquals(actualResult, expectedResult);
        
        // mocks verify
        verify(nodeWeightCalculator1, times(1)).calculateWeight(node);
        verify(nodeWeightCalculator2, times(1)).calculateWeight(node);
        verify(nodeWeightCalculator3, times(1)).calculateWeight(node);
        
    }
    
}