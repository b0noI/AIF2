package io.aif.language.semantic;

import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.DoubleSummaryStatistics;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class SemanticWordTest {

    @Test(groups = "unit-test")
    public void testWeight() {
        final Double expectedResult = .5;

        final INodeWeightCalculator<IWord> mockNodeWeightCalculator = mock(INodeWeightCalculator.class);

        final SemanticWord testInstance = new SemanticWord(null, mockNodeWeightCalculator);

        when(mockNodeWeightCalculator.calculateWeight(testInstance)).thenReturn(expectedResult);

        final Double actualResult = testInstance.weight();

        assertEquals(actualResult, expectedResult);
    }

}