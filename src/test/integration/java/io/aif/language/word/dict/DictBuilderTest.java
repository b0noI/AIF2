package io.aif.language.word.dict;

import io.aif.common.FileHelper;
import io.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IDict;
import io.aif.language.word.comparator.ISetComparator;
import opennlp.tools.formats.ad.ADSentenceStream;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by b0noI on 31/10/14.
 */
public class DictBuilderTest {

    @Test(groups = "experimental")
    public void test1() throws Exception {
        String text;
        long before = System.nanoTime();
        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream("aif_article.txt")) {
            text = FileHelper.readAllText(modelResource);
        }

        TokenSplitter tokenSplitter = new TokenSplitter();
        AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        List<String> tokens = tokenSplitter.split(text);
        List<List<String>> sentences = sentenceSplitter.split(tokens);
        List<String> filteredTokens = sentences.stream().flatMap(List::stream).collect(Collectors.toList());

        ITokenComparator tokenComparator = ITokenComparator.defaultComparator();
        ISetComparator setComparator = ISetComparator.createDefaultInstance(tokenComparator);
        DictBuilder dictBuilder = new DictBuilder(setComparator, tokenComparator);
        IDict dict = dictBuilder.build(filteredTokens);

        long after = System.nanoTime();
        long delta = (after - before) / 1000_000_000;
        System.out.println(dict);
        // 180 sec
        // 122 best
    }

}
