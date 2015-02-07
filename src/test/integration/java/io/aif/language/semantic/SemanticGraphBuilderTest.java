package io.aif.language.semantic;

import io.aif.common.FileHelper;
import io.aif.language.common.IDict;
import io.aif.language.common.IDictBuilder;
import io.aif.language.common.IMapper;
import io.aif.language.common.ISearchable;
import io.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.word.IWord;
import io.aif.language.word.dict.DictBuilder;
import io.aif.language.word.dict.WordPlaceHolderMapper;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class SemanticGraphBuilderTest {

    @Test
    public void testMap() throws Exception {
        String text;
        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream("Whiffet+Squirrel.txt")) {
            text = FileHelper.readAllText(modelResource);
        }

        final TokenSplitter tokenSplitter = new TokenSplitter();
        final AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        final List<String> tokens = tokenSplitter.split(text);
        final List<List<String>> sentences = sentenceSplitter.split(tokens);
        final List<String> filteredTokens = sentences.stream().flatMap(List::stream).collect(Collectors.toList());

        final IDictBuilder dictBuilder = new DictBuilder();
        final IDict<IWord> dict = dictBuilder.build(filteredTokens);
        final IMapper<Collection<String>, List<IWord.IWordPlaceholder>> toWordPlaceHolderMapper = new WordPlaceHolderMapper((ISearchable<String, IWord>)dict);
        final List<IWord.IWordPlaceholder> placeholders = toWordPlaceHolderMapper.map(filteredTokens);
        
        final SemanticDictBuilder3 semanticDictBuilder3 = new SemanticDictBuilder3(5);
        final ISemanticDict semanticDict = semanticDictBuilder3.build(placeholders);
        final List<ISemanticNode<IWord>> sortedNodes = semanticDict.getWords().stream().sorted((w1, w2) -> ((Double)w1.weight()).compareTo(w2.weight())).collect(Collectors.toList());
        final List<ISemanticNode<IWord>> invertedSortedNodes = semanticDict.getWords().stream().sorted((w2, w1) -> ((Double)w1.weight()).compareTo(w2.weight())).collect(Collectors.toList());
        System.out.println(sortedNodes);
    }
}