package io.aif.language.mapper;


public class TokensToWordsMapperTest {

//    @Test(groups = "unit-test")
//    public void testMap() throws Exception {
//        List<List<String>> tokenizedSentences = Arrays.asList(
//                Arrays.asList("I", "went", "to", "kandy"),
//                Arrays.asList("You", "are", "my", "sunshine")
//        );
//        ITokenComparator mockComparator = mock(ITokenComparator.class);
//        tokenizedSentences
//                .stream()
//                .flatMap(list -> list.stream())
//                .forEach(token -> when(mockComparator.compare(token, token)).thenReturn(1.0));
//
//        List<List<AbstractWord>> expected = new ArrayList<>();
//        for (List<String> tokens : tokenizedSentences) {
//            List<AbstractWord> tmp = tokens
//                    .stream()
//                    .map(token -> new Word(token, mockComparator))
//                    .collect(Collectors.toList());
//            expected.add(tmp);
//        }
//
//        DictBuilder mapper = new DictBuilder(mockComparator);
//        List<List<AbstractWord>> actual = mapper.mapAll(tokenizedSentences);
//        verifyZeroInteractions(mockComparator);
//        assertEquals(actual, expected);
//    }

//    @Test(groups = "unit-test")
//    public void testMapEmptyInput() throws Exception {
//        ITokenComparator mockComparator = mock(ITokenComparator.class);
//        List<List<String>> input = new ArrayList<>();
//        List<List<AbstractWord>> expected = new ArrayList<>();
//
//        DictBuilder mapper = new DictBuilder(mockComparator);
//        List<List<AbstractWord>> actual = mapper.mapAll(input);
//        verifyZeroInteractions(mockComparator);
//        assertEquals(actual, expected);
//    }
}