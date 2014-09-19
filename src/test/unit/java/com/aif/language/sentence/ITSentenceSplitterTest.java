package com.aif.language.sentence;

import com.aif.common.FileHelper;
import com.aif.language.sentence.separators.extractors.ISentenceSeparatorExtractor;
import com.aif.language.token.TokenSplitter;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;

import static junit.framework.Assert.assertTrue;


public class ITSentenceSplitterTest {

    private static final String TEXT_RESOURCE_PATH = "for_sentence_split_test_4939.txt";

    @Test(groups = "unit-tests")
    public void testSplit() throws Exception {
        try(final InputStream modelResource = ITSentenceSplitterTest.class.getResourceAsStream(TEXT_RESOURCE_PATH)) {

            final String text = FileHelper.readAllText(modelResource);

            final TokenSplitter tokenSplitter = new TokenSplitter();
            final SentenceSplitter sentenceSplitter = new SentenceSplitter(ISentenceSeparatorExtractor.Type.PROBABILITY.getInstance());

            final List<List<String>> actualResult = sentenceSplitter.split(tokenSplitter.split(text));

            assertTrue(actualResult.size() > 0);
        }
    }

}