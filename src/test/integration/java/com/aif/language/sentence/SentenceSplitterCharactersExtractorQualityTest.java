package com.aif.language.sentence;

import com.aif.common.FileHelper;
import com.aif.language.common.ISplitter;
import com.aif.language.token.TokenSplitter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class SentenceSplitterCharactersExtractorQualityTest {

    private static final String TEXT_FILE_NAME = "46800-0.txt";

    @Test(groups = { "quality-test", "acceptance-tests" })
    public void testSeparatorExtractionQuality() throws Exception {
        // input arguments
        String inputText;
        try(InputStream modelResource = SentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream(TEXT_FILE_NAME)) {
            inputText = FileHelper.readAllText(modelResource);
        }
        final TokenSplitter tokenSplitter = new TokenSplitter();
        final List<String> inputToken = tokenSplitter.split(inputText);

        // expected results
        final List<Character> expectedResult = Arrays.asList(new Character[]{
                '.', '(', ')',
                ':', '\"', '#',
                ';', '‘', '“',
                ',', '#', '%',
                '%', '\'', '?',
                '!', '[', ']'
        });

        // creating test instance
        final ISentenceSeparatorExtractor testInstance = ISentenceSeparatorExtractor.Type.STAT.getInstance();

        // execution test
        final List<Character> actualResult = testInstance.extract(inputToken).get();

        // result assert

        long correct  = actualResult
                .stream()
                .filter(ch -> expectedResult.contains(ch))
                .count();
        double result = (correct * 2.) / (double)(expectedResult.size() + actualResult.size());
        assertTrue(String.format("result is: %f", result), result > 0.53);

    }
}
