package io.aif.language.fact;

import io.aif.graph.simple.ISimpleGraph;
import io.aif.graph.simple.ISimpleGraphBuilder;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;

public class DFSTest {

    @Test(groups = "unit-tests")
    public void testFindPath() throws Exception {

        final ISimpleGraphBuilder<Integer> graphBuilder = ISimpleGraphBuilder.defaultBuilder();
        graphBuilder.connect(1, 2);
        graphBuilder.connect(1, 3);
        graphBuilder.connect(2, 4);
        graphBuilder.connect(3, 4);

        List<List<Integer>> expected = new ArrayList<List<Integer>>() {{
            add(Arrays.asList(1, 3, 4));
            add(Arrays.asList(1, 2, 4));
        }};
        runAndCompare(graphBuilder, expected, 1, 4);
    }

    @Test(groups = "unit-tests")
    public void testFindPath2() throws Exception {

        final ISimpleGraphBuilder<Integer> graphBuilder = ISimpleGraphBuilder.defaultBuilder();
        graphBuilder.connect(1, 2);
        graphBuilder.connect(1, 3);
        graphBuilder.connect(2, 3);
        graphBuilder.add(4);

        List<List<Integer>> expected = Collections.emptyList();
        runAndCompare(graphBuilder, expected, 1, 4);
    }

    @Test(groups = "unit-tests")
    public void testFindPath3() throws Exception {

        final ISimpleGraphBuilder<Integer> graphBuilder = ISimpleGraphBuilder.defaultBuilder();
        graphBuilder.add(1);
        graphBuilder.add(2);

        List<List<Integer>> expected = Collections.emptyList();
        runAndCompare(graphBuilder, expected, 1, 4);
    }

    @Test(groups = "unit-tests")
    public void testFindPath4() throws Exception {

        final ISimpleGraphBuilder<Integer> graphBuilder = ISimpleGraphBuilder.defaultBuilder();
        graphBuilder.connect(1, 3);
        graphBuilder.connect(3, 4);
        graphBuilder.connect(4, 5);
        graphBuilder.connect(5, 2);
        graphBuilder.connect(1, 2);

        List<List<Integer>> expected = new ArrayList<List<Integer>>() {{
            add(Arrays.asList(1, 2));
            add(Arrays.asList(1, 3, 4, 5, 2));
        }};
        runAndCompare(graphBuilder, expected, 1, 2);
    }

    @Test(groups = "unit-tests")
    public void testFindPathForCircularTraversal() throws Exception {

        final ISimpleGraphBuilder<Integer> graphBuilder = ISimpleGraphBuilder.defaultBuilder();
        graphBuilder.connect(1, 2);
        graphBuilder.connect(1, 3);
        graphBuilder.connect(1, 4);
        graphBuilder.connect(2, 3);
        graphBuilder.connect(2, 4);
        graphBuilder.connect(2, 5);
        graphBuilder.connect(3, 4);
        graphBuilder.connect(3, 5);
        graphBuilder.connect(4, 5);

        DFS<Integer> traverser =  new DFS<>(graphBuilder.build());
        List<List<Integer>> actual = traverser.findPath(1, 5);
        assertTrue(actual.size() > 0);
    }

    private void runAndCompare(ISimpleGraphBuilder<Integer> graphBuilder, List<List<Integer>> expected, Integer src, Integer dst) {
        final ISimpleGraph<Integer> graph = graphBuilder.build();

        DFS<Integer> traverser =  new DFS<>(graph);
        List<List<Integer>> actual = traverser.findPath(src, dst);
        actual.forEach(item -> assertTrue(expected.contains(item), item.toString()));
        assertEquals(actual.size(), expected.size());
    }
}