package com.aif.language.word.dict;

import com.aif.common.FileHelper;
import com.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import com.aif.language.token.TokenSplitter;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by b0noI on 31/10/14.
 */
public class DictBuilderTest {

    @Test(groups = "experimental")
    public void test1() throws Exception {
        String text;

        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream("Afloat+on+the+Flood.txt")) {
            text = FileHelper.readAllText(modelResource);
        }

        TokenSplitter tokenSplitter = new TokenSplitter();
        List<String> tokens = tokenSplitter.split(text);

        DictBuilder dictBuilder = new DictBuilder();
        dictBuilder.build(tokens);
    }

}
