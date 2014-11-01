package io.aif.language.mapper;

public class WordsToPlaceHoldersMapperTest {

//    @Test(groups = "unit-test")
//    public void testMapNoWordsMerged() throws Exception {
//        List<List<String>> tokenizedSentences = Arrays.asList(
//                Arrays.asList("I", "went", "to", "kandy"),
//                Arrays.asList("You", "are", "my", "sunshine")
//        );
//
//        ITokenComparator mockComparator = mock(ITokenComparator.class);
//        tokenizedSentences
//                .stream()
//                .flatMap(list -> list.stream())
//                .forEach(token -> when(mockComparator.compare(token, token)).thenReturn(1.0));
//
//        List<List<AbstractWord>> wordLists = new WordsToPlaceHoldersMapper(mockComparator).mapAll(tokenizedSentences);
//
//        List<List<AbstractWord.WordPlaceHolder>> expected = new ArrayList<>();
//        for (List<AbstractWord> words : wordLists) {
//            List<AbstractWord.WordPlaceHolder> tmp = words
//                    .stream()
//                    .map(word -> word.new WordPlaceHolder(word.getRootToken()))
//                    .collect(Collectors.toList())
//            ;
//            expected.add(tmp);
//        }
//
//        IMapper<List<List<AbstractWord>>, List<List<AbstractWord.WordPlaceHolder>>> mapper = new WordsToPlaceHoldersMapper();
//        List<List<AbstractWord.WordPlaceHolder>> actual = mapper.map(wordLists);
//        assertEquals(actual, expected);
//    }

//    @Test(groups = "unit-test")
//    public void testMapEmptyWordsList() throws Exception {
//        ITokenComparator mockComparator = mock(ITokenComparator.class);
//        List<List<AbstractWord>> expected = new ArrayList<>();
//        List<List<AbstractWord>> wordLists = new ArrayList<>();
//
//        IMapper<List<List<AbstractWord>>, List<List<AbstractWord.WordPlaceHolder>>> mapper = new WordsToPlaceHoldersMapper();
//        List<List<AbstractWord.WordPlaceHolder>> actual = mapper.map(wordLists);
//        assertEquals(actual, expected);
//    }

//    @Test(groups = "unit-test")
//    public void testMapWordsMergedInASentence() throws Exception {
//        List<List<String>> tokenizedSentences = Arrays.asList(
//                Arrays.asList("I", "went", "to", "kandy", "to", "eat", "candy")
//        );
//
//        ITokenComparator mockComparator = mock(ITokenComparator.class);
//        when(mockComparator.compare("to", "to")).thenReturn(1.0);
//        when(mockComparator.compare("kandy", "candy")).thenReturn(1.0);
//
//        List<List<AbstractWord>> wordLists = new DictBuilder(mockComparator).mapAll(tokenizedSentences);
//
//        AbstractWord wordI = new Word("I", mockComparator);
//        AbstractWord wordWent = new Word("went", mockComparator);
//        AbstractWord wordTo = new Word("to", mockComparator);
//        AbstractWord wordKandy = new Word("kandy", mockComparator);
//        wordTo.merge(new Word("to", mockComparator));
//        AbstractWord wordEat = new Word("eat", mockComparator);
//        wordKandy.merge(new Word("candy", mockComparator));
//        List<List<AbstractWord.WordPlaceHolder>> expected = Arrays.asList(
//            Arrays.asList(
//                    wordI.new WordPlaceHolder("I"),
//                    wordWent.new WordPlaceHolder("went"),
//                    wordTo.new WordPlaceHolder("to"),
//                    wordKandy.new WordPlaceHolder("kandy"),
//                    wordTo.new WordPlaceHolder("to"),
//                    wordEat.new WordPlaceHolder("eat"),
//                    wordKandy.new WordPlaceHolder("candy"))
//        );
//
//        IMapper<List<List<AbstractWord>>, List<List<AbstractWord.WordPlaceHolder>>> mapper = new WordsToPlaceHoldersMapper();
//        List<List<AbstractWord.WordPlaceHolder>> actual = mapper.map(wordLists);
//        assertEquals(actual, expected);
//    }
}