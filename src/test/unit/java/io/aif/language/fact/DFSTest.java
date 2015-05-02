package io.aif.language.fact;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;

public class DFSTest {

    @Test(groups = "unit-tests")
    public void testFindPath() throws Exception {

        FactGraph<Integer> graph = new FactGraph<>();
        graph.connect(1, 2);
        graph.connect(1, 3);
        graph.connect(2, 4);
        graph.connect(3, 4);

        List<List<Integer>> expected = new ArrayList<List<Integer>>() {{
            add(Arrays.asList(1, 3, 4));
            add(Arrays.asList(1, 2, 4));
        }};
        runAndCompare(graph, expected, 1, 4);
    }

    @Test(groups = "unit-tests")
    public void testFindPath2() throws Exception {

        FactGraph<Integer> graph = new FactGraph<>();
        graph.connect(1, 2);
        graph.connect(1, 3);
        graph.connect(2, 3);
        graph.add(4);

        List<List<Integer>> expected = Collections.emptyList();
        runAndCompare(graph, expected, 1, 4);
    }

    @Test(groups = "unit-tests")
    public void testFindPath3() throws Exception {

        FactGraph<Integer> graph = new FactGraph<>();
        graph.add(1);
        graph.add(2);

        List<List<Integer>> expected = Collections.emptyList();
        runAndCompare(graph, expected, 1, 4);
    }

    @Test(groups = "unit-tests")
    public void testFindPath4() throws Exception {

        FactGraph<Integer> graph = new FactGraph<>();
        graph.connect(1, 3);
        graph.connect(3, 4);
        graph.connect(4, 5);
        graph.connect(5, 2);
        graph.connect(1, 2);

        List<List<Integer>> expected = new ArrayList<List<Integer>>() {{
            add(Arrays.asList(1, 2));
            add(Arrays.asList(1, 3, 4, 5, 2));
        }};
        runAndCompare(graph, expected, 1, 2);
    }

    @Test(groups = "unit-tests")
    public void testFindPathForCircularTraversal() throws Exception {

        FactGraph<Integer> graph = new FactGraph<>();
        graph.connect(1, 2);
        graph.connect(1, 3);
        graph.connect(1, 4);
        graph.connect(2, 3);
        graph.connect(2, 4);
        graph.connect(2, 5);
        graph.connect(3, 4);
        graph.connect(3, 5);
        graph.connect(4, 5);

        DFS<Integer> traverser =  new DFS<>(graph);
        List<List<Integer>> actual = traverser.findPath(1, 5);
        assertTrue(actual.size() > 0);
    }

    private void runAndCompare(FactGraph<Integer> graph, List<List<Integer>> expected, Integer src, Integer dst) {
        DFS<Integer> traverser =  new DFS<>(graph);
        List<List<Integer>> actual = traverser.findPath(src, dst);
        actual.forEach(item -> assertTrue(expected.contains(item), item.toString()));
        assertEquals(actual.size(), expected.size());
    }
}