package io.aif.language.fact;

public class FactQueryTest {

//    private final IFuzzyBoolean trueFuzzy = new FuzzyBoolean(.2, .1);
//    private final IFuzzyBoolean falseFuzzy = new FuzzyBoolean(.1, .2);
//
//    @Test(groups = "unit-tests")
//    public void testFindPath() throws Exception {
//        IWord mockNode0 = mock(IWord.class);
//        when(mockNode0.isProperNoun()).thenReturn(trueFuzzy);
//
//        IWord mockNode1 = mock(IWord.class);
//        when(mockNode1.isProperNoun()).thenReturn(falseFuzzy);
//        List<IWord> sentence1 = Arrays.asList(mockNode0, mockNode1);
//        IFact fact1 = new Fact(sentence1);
//
//        ISemanticNode<IWord> mockNode2 = mock(ISemanticNode.class);
//        when(mockNode2.isProperNoun()).thenReturn(trueFuzzy);
//        List<IWord> sentence2 = Arrays.asList(mockNode1, mockNode2);
//        IFact fact2 = new Fact(sentence2);
//
//        ISemanticNode<IWord> mockNode3 = mock(ISemanticNode.class);
//        when(mockNode3.isProperNoun()).thenReturn(falseFuzzy);
//        List<IWord> sentence3 = Arrays.asList(mockNode1, mockNode3);
//        IFact fact3 = new Fact(sentence3);
//
//        ISemanticNode<IWord> mockNode4 = mock(ISemanticNode.class);
//        when(mockNode4.isProperNoun()).thenReturn(trueFuzzy);
//        List<IWord> sentence4 = Arrays.asList(mockNode3, mockNode4);
//        IFact fact4 = new Fact(sentence4);
//
//        List<IFact> expected = Arrays.asList(fact1, fact2, fact3, fact4);
//
//        FactGraph<IFact> g = new FactGraph<>();
//        g.connect(fact1, fact2);
//        g.connect(fact2, fact3);
//        g.connect(fact3, fact4);
//
//        FactQuery fq = new FactQuery(g);
//        List<IFact> actual = fq.findPath(mockNode0, mockNode4).get().get(0);
//
//        assertEquals(actual, expected);
//    }
//
//    @Test(groups = "unit-tests")
//    public void testFindPathNoConnection() throws Exception {
//        ISemanticNode<IWord> mockNode0 = mock(ISemanticNode.class);
//        when(mockNode0.isProperNoun()).thenReturn(trueFuzzy);
//
//        ISemanticNode<IWord> mockNode1 = mock(ISemanticNode.class);
//        when(mockNode1.isProperNoun()).thenReturn(falseFuzzy);
//        List<IWord> sentence1 = Arrays.asList(mockNode0, mockNode1);
//        IFact fact1 = new Fact(sentence1);
//
//        ISemanticNode<IWord> mockNode2 = mock(ISemanticNode.class);
//        when(mockNode2.isProperNoun()).thenReturn(trueFuzzy);
//        List<IWord> sentence2 = Arrays.asList(mockNode1, mockNode2);
//        IFact fact2 = new Fact(sentence2);
//
//        ISemanticNode<IWord> mockNode3 = mock(ISemanticNode.class);
//        when(mockNode3.isProperNoun()).thenReturn(falseFuzzy);
//        List<IWord> sentence3 = Arrays.asList(mockNode1, mockNode3);
//        IFact fact3 = new Fact(sentence3);
//
//        ISemanticNode<IWord> mockNode4 = mock(ISemanticNode.class);
//        when(mockNode4.isProperNoun()).thenReturn(trueFuzzy);
//        List<IWord> sentence4 = Arrays.asList(mockNode4);
//        IFact fact4 = new Fact(sentence4);
//
//        boolean expected = false;
//
//        FactGraph<IFact> g = new FactGraph<>();
//        g.connect(fact1, fact2);
//        g.connect(fact2, fact3);
//        g.add(fact4);
//
//        FactQuery fq = new FactQuery(g);
//        Optional<List<List<IFact>>> actual = fq.findPath(mockNode0, mockNode4);
//
//        assertEquals(actual.isPresent(), expected);
//    }
//
//    @Test(groups = "unit-tests")
//    public void testFindPathProperNounsInTheSameFact() throws Exception {
//        ISemanticNode<IWord> mockNode0 = mock(ISemanticNode.class);
//        when(mockNode0.isProperNoun()).thenReturn(trueFuzzy);
//
//        ISemanticNode<IWord> mockNode1 = mock(ISemanticNode.class);
//        when(mockNode1.isProperNoun()).thenReturn(falseFuzzy);
//        List<IWord> sentence1 = Arrays.asList(mockNode0, mockNode1);
//        IFact fact1 = new Fact(sentence1);
//
//        ISemanticNode<IWord> mockNode2 = mock(ISemanticNode.class);
//        when(mockNode2.isProperNoun()).thenReturn(trueFuzzy);
//        List<IWord> sentence2 = Arrays.asList(mockNode1, mockNode2);
//        IFact fact2 = new Fact(sentence2);
//
//        ISemanticNode<IWord> mockNode3 = mock(ISemanticNode.class);
//        when(mockNode3.isProperNoun()).thenReturn(falseFuzzy);
//        List<IWord> sentence3 = Arrays.asList(mockNode1, mockNode3);
//        IFact fact3 = new Fact(sentence3);
//
//        ISemanticNode<IWord> mockNode4 = mock(ISemanticNode.class);
//        when(mockNode4.isProperNoun()).thenReturn(trueFuzzy);
//        List<IWord> sentence4 = Arrays.asList(mockNode4, mockNode3);
//        IFact fact4 = new Fact(sentence4);
//
//        ISemanticNode<IWord> mockNode5 = mock(ISemanticNode.class);
//        when(mockNode5.isProperNoun()).thenReturn(trueFuzzy);
//        List<IWord> sentence5 = Arrays.asList(mockNode5, mockNode2);
//        IFact fact5 = new Fact(sentence5);
//
//        List<IFact> expected = Arrays.asList(fact5);
//
//        FactGraph<IFact> g = new FactGraph<>();
//        g.connect(fact1, fact2);
//        g.connect(fact2, fact3);
//        g.connect(fact3, fact4);
//        g.connect(fact2, fact5);
//
//        FactQuery fq = new FactQuery(g);
//        List<IFact> actual = fq.findPath(mockNode2, mockNode5).get().get(0);
//
//        assertEquals(actual, expected);
//    }
//
//    @Test(groups = "unit-tests")
//    public void testFindPathMultiplePathsWithEqualCost() throws Exception {
//        ISemanticNode<IWord> mockNode0 = mock(ISemanticNode.class);
//        when(mockNode0.isProperNoun()).thenReturn(trueFuzzy);
//        when(mockNode0.toString()).thenReturn("mockNode0");
//
//        ISemanticNode<IWord> mockNode1 = mock(ISemanticNode.class);
//        when(mockNode1.isProperNoun()).thenReturn(falseFuzzy);
//        when(mockNode1.toString()).thenReturn("mockNode1");
//
//        ISemanticNode<IWord> mockNode2 = mock(ISemanticNode.class);
//        when(mockNode2.isProperNoun()).thenReturn(trueFuzzy);
//        when(mockNode2.toString()).thenReturn("mockNode2");
//
//        List<IWord> sentence1 = Arrays.asList(mockNode0, mockNode1, mockNode2);
//        IFact fact1 = new Fact(sentence1);
//
//        List<IWord> sentence2 = Arrays.asList(mockNode1);
//        IFact fact2 = new Fact(sentence2);
//
//        List<IWord> sentence3 = Arrays.asList(mockNode2);
//        IFact fact3 = new Fact(sentence3);
//
//        ISemanticNode<IWord> mockNode3 = mock(ISemanticNode.class);
//        when(mockNode3.isProperNoun()).thenReturn(trueFuzzy);
//        when(mockNode3.toString()).thenReturn("mockNode4");
//        List<IWord> sentence4 = Arrays.asList(mockNode3, mockNode1, mockNode2);
//        IFact fact4 = new Fact(sentence4);
//
//        List<List<IFact>> expected = new ArrayList<List<IFact>>() {{
//            add(Arrays.asList(fact1, fact2, fact4));
//            add(Arrays.asList(fact1, fact3, fact4));
//        }};
//
//        FactGraph<IFact> g = new FactGraph<>();
//        g.connect(fact1, fact2);
//        g.connect(fact1, fact3);
//        g.connect(fact2, fact4);
//        g.connect(fact3, fact4);
//
//        FactQuery fq = new FactQuery(g);
//        List<List<IFact>> actual = fq.findPath(mockNode0, mockNode3).get();
//
//        actual.forEach(item -> assertTrue(expected.contains(item)));
//    }
}