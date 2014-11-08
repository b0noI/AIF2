package io.aif.language.word.dict;

import io.aif.common.FileHelper;
import io.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import io.aif.language.token.TokenSplitter;
import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IDict;
import io.aif.language.word.comparator.ISetComparator;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;


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

        TokenSplitter tokenSplitter = new TokenSplitter();
        List<String> tokens = tokenSplitter.split(text);

        ITokenComparator tokenComparator = ITokenComparator.defaultComparator();
        ISetComparator setComparator = ISetComparator.createDefaultInstance(tokenComparator);
        DictBuilder dictBuilder = new DictBuilder(setComparator, tokenComparator);
        IDict dict = dictBuilder.build(tokens);
        System.out.println(dict);
    }

}
