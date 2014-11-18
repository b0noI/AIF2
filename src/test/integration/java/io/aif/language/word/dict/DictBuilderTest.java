package io.aif.language.word.dict;

import io.aif.common.FileHelper;
import io.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IDict;
import io.aif.language.word.comparator.ISetComparator;
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

        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream("Afloat+on+the+Flood_small.txt")) {
            text = FileHelper.readAllText(modelResource);
        }

        final TokenSplitter tokenSplitter = new TokenSplitter();
        final AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        final List<List<String>> sentences = sentenceSplitter.split(tokenSplitter.split(text));
        final List<String> tokens = sentences.stream().flatMap(List::stream).collect(Collectors.toList());

        final ITokenComparator tokenComparator = ITokenComparator.defaultComparator();
        final ISetComparator setComparator = ISetComparator.createDefaultInstance(tokenComparator);
        final DictBuilder dictBuilder = new DictBuilder(setComparator, tokenComparator);
        final IDict dict = dictBuilder.build(tokens);
        System.out.println(dict);
    }

}
