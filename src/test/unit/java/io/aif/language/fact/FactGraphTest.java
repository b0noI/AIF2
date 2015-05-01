package io.aif.language.fact;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;

public class FactGraphTest {

    @Test(groups = "unit-tests")
    public void testConnectAndGetNeighbours() throws Exception {
        IFact mockFact1 = mock(IFact.class);
        IFact mockFact2 = mock(IFact.class);
        IFact mockFact3 = mock(IFact.class);

        Set<IFact> expected = new HashSet<>(Arrays.asList(mockFact2, mockFact3));

        FactGraph<IFact> g = new FactGraph<>();
        g.connect(mockFact1, mockFact2);
        g.connect(mockFact1, mockFact3);
        Set<IFact> actual = g.getNeighbours(mockFact1).get();

        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testConnectToNull() throws Exception {
        IFact mockFact1 = mock(IFact.class);

        Set<IFact> expected = new HashSet<>();
        expected.add(null);

        FactGraph<IFact> g = new FactGraph<>();
        g.connect(mockFact1, null);
        Set<IFact> actual = g.getNeighbours(mockFact1).get();

        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testAdd() throws Exception {
        IFact mockFact1 = mock(IFact.class);

        Set<IFact> expected = new HashSet<>(Arrays.asList(mockFact1));

        FactGraph<IFact> g = new FactGraph<>();
        g.add(mockFact1);
        Set<IFact> actual = g.getAll().get();

        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testAddNull() throws Exception {
        Set<IFact> expected = new HashSet<>();
        expected.add(null);

        FactGraph<IFact> g = new FactGraph<>();
        g.add(null);
        Set<IFact> actual = g.getAll().get();

        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testGetNeighboursEmptyGraph() throws Exception {
        IFact mockFact1 = mock(IFact.class);
        FactGraph<IFact> g = new FactGraph<>();
        assertEquals(g.getNeighbours(mockFact1).isPresent(), false);
    }

    @Test(groups = "unit-tests")
    public void testGetNeighboursForNonExistentVertex() throws Exception {
        IFact mockFact1 = mock(IFact.class);
        IFact mockFact2 = mock(IFact.class);
        IFact mockFact3 = mock(IFact.class);
        IFact mockFact4 = mock(IFact.class);

        FactGraph<IFact> g = new FactGraph<>();
        g.connect(mockFact1, mockFact2);
        g.connect(mockFact1, mockFact3);
        assertEquals(g.getNeighbours(mockFact4).isPresent(), false);
    }

    @Test(groups = "unit-tests")
    public void testGetNeighboursForVertexWithoutConnections() throws Exception {
        IFact mockFact1 = mock(IFact.class);
        IFact mockFact2 = mock(IFact.class);
        IFact mockFact3 = mock(IFact.class);
        IFact mockFact4 = mock(IFact.class);

        FactGraph<IFact> g = new FactGraph<>();
        g.connect(mockFact1, mockFact2);
        g.connect(mockFact1, mockFact3);
        g.add(mockFact4);
        assertEquals(g.getNeighbours(mockFact4).isPresent(), false);
    }

    @Test(groups = "unit-tests")
    public void testGetAll() throws Exception {
        IFact mockFact1 = mock(IFact.class);
        IFact mockFact2 = mock(IFact.class);
        IFact mockFact3 = mock(IFact.class);
        IFact mockFact4 = mock(IFact.class);

        Set<IFact> expected = new HashSet<>(Arrays.asList(mockFact1, mockFact2, mockFact3, mockFact4));

        FactGraph<IFact> g = new FactGraph<>();
        g.connect(mockFact1, mockFact2);
        g.connect(mockFact1, mockFact3);
        g.add(mockFact4);
        Set<IFact> actual = g.getAll().get();
        assertEquals(actual, expected);
    }

    @Test(groups = "unit-tests")
    public void testGetAllEmptyGraph() throws Exception {
        FactGraph<IFact> g = new FactGraph<>();
        assertEquals(g.getAll().isPresent(), false);
    }
}