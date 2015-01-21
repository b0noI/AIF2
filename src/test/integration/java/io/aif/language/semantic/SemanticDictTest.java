package io.aif.language.semantic;

import io.aif.common.FileHelper;
import io.aif.language.common.IDict;
import io.aif.language.common.IDictBuilder;
import io.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.word.IWord;
import io.aif.language.word.dict.DictBuilder;
import io.aif.language.word.dict.DictBuilderIntegTest;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class SemanticDictTest {

    @Test
    public void testSemanticDict() throws Exception {

//        final String article = "aif_article.txt";
        final String article = "Afloat+on+the+Flood.txt";


        String text;
        long before = System.nanoTime();
        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream(article)) {
            text = FileHelper.readAllText(modelResource);
        }

        final TokenSplitter tokenSplitter = new TokenSplitter();
        final AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        final List<String> tokens = tokenSplitter.split(text);
        final List<List<String>> sentences = sentenceSplitter.split(tokens);
        final List<String> filteredTokens = sentences.stream().flatMap(List::stream).collect(Collectors.toList());

        IDictBuilder dictBuilder = new DictBuilder();
        IDict<IWord> dict = dictBuilder.build(filteredTokens);

        long after = System.nanoTime();
        long delta = (after - before) / 1000_000_000;

        final ISemanticDict semanticDict = SemanticDictBuilder.getInstance().build(dict);

        final List<ISemanticNode<IWord>> nodes = semanticDict
                .getWords()
                .stream()
                .sorted((n1, n2) -> ((Double) n2.weight()).compareTo(n1.weight()))
                .collect(Collectors.toList());
        System.out.println(nodes);
    }

}