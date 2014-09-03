package com.aif.language.sentence;

import com.aif.common.FileHelper;
import com.aif.language.common.ISplitter;
import com.aif.language.token.TokenSplitter;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertTrue;


public class SentenceSplitterTest {

    private static final String TEXT_RESOURCE_PATH = "for_sentence_split_test.txt";

    @Test(groups = "unit-tests")
    public void testSplit() throws Exception {
        try(final InputStream modelResource = SentenceSplitterTest.class.getResourceAsStream(TEXT_RESOURCE_PATH)) {

            final String text = FileHelper.readAllTextFromFile(modelResource);

            final TokenSplitter tokenSplitter = new TokenSplitter();
            final SentenceSplitter sentenceSplitter = new SentenceSplitter(ISentenceSeparatorExtractor.Type.STAT.getInstance());

            final List<List<String>> actualResult = sentenceSplitter.split(tokenSplitter.split(text));

            assertTrue(actualResult.size() > 0);
        }
    }

}