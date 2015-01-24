package io.aif.language.semantic;

import io.aif.language.semantic.weights.node.INodeWeightCalculator;
import io.aif.language.word.IWord;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

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

    @Test(groups = "unit-test")
    public void testConnectionWeight() {
        final Double expectedResult = .1;

        final ISemanticNode<IWord> mockSemanticNode = mock(ISemanticNode.class);

        final SemanticWord testInstance = new SemanticWord(null){

            @Override
            protected double getAverageDistance(final ISemanticNode<IWord> node) {
                assertEquals(node, mockSemanticNode);
                return .2;
            }

            @Override
            protected double maxConnection() {
                return 2.;
            }

        };

        final Double actualResult = testInstance.connectionWeight(mockSemanticNode);

        assertEquals(actualResult, expectedResult);
    }

    @Test(groups = "unit-test")
    public void testConnectedItems() {
        final Set<ISemanticNode<IWord>> expectedAnswer = mock(Set.class);

        final Map<ISemanticNode<IWord>, SemanticWord.Edge> mockConnections = mock(Map.class);
        when(mockConnections.keySet()).thenReturn(expectedAnswer);

        final SemanticWord testInstance = new SemanticWord(null, null, mockConnections);

        final Set<ISemanticNode<IWord>> actualAnswer = testInstance.connectedItems();

        assertEquals(actualAnswer, expectedAnswer);
    }

    @Test(groups = "unit-test")
    public void testMaxConnection() {
        final Double expectedResult = .8;

        final ISemanticNode<IWord> mockNode1 = mock(ISemanticNode.class);
        final ISemanticNode<IWord> mockNode2 = mock(ISemanticNode.class);
        final ISemanticNode<IWord> mockNode3 = mock(ISemanticNode.class);

        final SemanticWord.Edge edge1 = new SemanticWord.Edge();
        final SemanticWord.Edge edge2 = new SemanticWord.Edge();
        final SemanticWord.Edge edge3 = new SemanticWord.Edge();

        edge1.addDistance(expectedResult);
        edge2.addDistance(.5);
        edge3.addDistance(.2);

        final Map<ISemanticNode<IWord>, SemanticWord.Edge> connections = new HashMap<>();
        connections.put(mockNode1, edge1);
        connections.put(mockNode2, edge2);
        connections.put(mockNode3, edge3);

        final SemanticWord testInstance = new SemanticWord(null, null, connections);

        final Double actualResult = testInstance.maxConnection();

        assertEquals(actualResult, expectedResult);
    }

    @Test(groups = "unit-test")
    public void testMaxConnectionWhenAllEquals() {
        final Double expectedResult = .8;

        final ISemanticNode<IWord> mockNode1 = mock(ISemanticNode.class);
        final ISemanticNode<IWord> mockNode2 = mock(ISemanticNode.class);
        final ISemanticNode<IWord> mockNode3 = mock(ISemanticNode.class);

        final SemanticWord.Edge edge1 = new SemanticWord.Edge();
        final SemanticWord.Edge edge2 = new SemanticWord.Edge();
        final SemanticWord.Edge edge3 = new SemanticWord.Edge();

        edge1.addDistance(expectedResult);
        edge2.addDistance(expectedResult);
        edge3.addDistance(expectedResult);

        final Map<ISemanticNode<IWord>, SemanticWord.Edge> connections = new HashMap<>();
        connections.put(mockNode1, edge1);
        connections.put(mockNode2, edge2);
        connections.put(mockNode3, edge3);

        final SemanticWord testInstance = new SemanticWord(null, null, connections);

        final Double actualResult = testInstance.maxConnection();

        assertEquals(actualResult, expectedResult);
    }

    @Test(groups = "unit-test")
    public void testGetAverageDistance() {

        final Double expectedResult = .8;

        final ISemanticNode<IWord> inputNode = mock(ISemanticNode.class);

        final SemanticWord.Edge edge = new SemanticWord.Edge();

        edge.addDistance(expectedResult);

        final Map<ISemanticNode<IWord>, SemanticWord.Edge> connections = new HashMap<>();
        connections.put(inputNode, edge);

        final SemanticWord testInstance = new SemanticWord(null, null, connections);

        final Double actualResult = testInstance.maxConnection();

        assertEquals(actualResult, expectedResult);
    }

}