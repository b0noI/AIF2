package io.aif.language.semantic.weights.node.word;

import org.testng.annotations.Test;

public class ConnectionBasedWeightCalculatorTest {

  @Test(groups = "unit-tests")
  public void testCalculateWeight() throws Exception {
//        // input arguments
//        final IWord inputNode = mock(IWord.class);
//
//        // mocks
//        final ISemanticNode<IWord> mockConnection1 = mock(ISemanticNode.class);
//        final ISemanticNode<IWord> mockConnection2 = mock(ISemanticNode.class);
//        final ISemanticNode<IWord> mockConnection3 = mock(ISemanticNode.class);
//        final ISemanticNode<IWord> mockConnection4 = mock(ISemanticNode.class);
//
//        final IEdgeWeightCalculator<IWord> edgeWeightCalculator = mock(IEdgeWeightCalculator.class);
//        final Map<IWord, Map<IWord, List<Double>>> distancesGraph = mock(Map.class);
//
//        when(inputNode.connectedItems()).thenReturn(
//                new HashSet<>(Arrays.asList(mockConnection1, mockConnection2, mockConnection3, mockConnection4))
//        );
//        when(inputNode.connectionWeight(mockConnection1)).thenReturn(.1);
//        when(inputNode.connectionWeight(mockConnection2)).thenReturn(.2);
//        when(inputNode.connectionWeight(mockConnection3)).thenReturn(.3);
//        when(inputNode.connectionWeight(mockConnection3)).thenReturn(.4);
//
//        // expected results
//        final double expectedResult = 0.39992000000000005;
//
//        // creating test instance
//        final IWordWeightCalculator testInstance = new ConnectionBasedWeightCalculator(edgeWeightCalculator, distancesGraph);
//
//        // execution test
//        final double actualResult = testInstance.calculateW(inputNode);
//
//        // result assert
//        assertEquals(actualResult, expectedResult);
//
//        // mocks verify
//        verify(inputNode, times(1)).connectionWeight(mockConnection1);
//        verify(inputNode, times(1)).connectionWeight(mockConnection2);
//        verify(inputNode, times(1)).connectionWeight(mockConnection3);
//        verify(inputNode, times(1)).connectionWeight(mockConnection4);
//        verify(inputNode, times(1)).connectedItems();
  }

}