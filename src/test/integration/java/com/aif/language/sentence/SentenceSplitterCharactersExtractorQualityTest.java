package com.aif.language.sentence;

import com.aif.common.FileHelper;
import com.aif.language.common.ISplitter;
import com.aif.language.token.TokenSplitter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class SentenceSplitterCharactersExtractorQualityTest {

    @DataProvider(name = "path_provider")
    private static String[][] pathProvider() {
       return new String[][]{
               {"46800-0.txt"},
               {"for_sentence_split_test_4939.txt"},
               // {"for_sentence_split_test_opencorpora_RU_5000.txt"}, // This text should be switched on only after fixing issue #83
               {"RU_alice_in_the_wonderland.txt"}
        } ;
    }

    @Test(groups = { "quality-test", "acceptance-tests" }, dataProvider = "path_provider")
    public void testSeparatorExtractionQuality(final String path) throws Exception {
        // input arguments
        String inputText;
        try(InputStream modelResource = SentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream(path)) {
            inputText = FileHelper.readAllText(modelResource);
        }
        final TokenSplitter tokenSplitter = new TokenSplitter();
        final List<String> inputToken = tokenSplitter.split(inputText);

        // expected results
        final List<Character> expectedResult = Arrays.asList(new Character[]{
                '.', '(', ')',
                ':', '\"', '#',
                ';', '‘', '“',
                ',', '\'', '?',
                '!'
        });
        final List<Character> mandatoryCharacters = Arrays.asList(new Character[]{
                '.', ',', '(', ')', '?'
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
        double result = (double)correct / (double)expectedResult.size();
        assertTrue(String.format("result is: %f", result), result > 0.65);

        mandatoryCharacters.forEach(ch ->
            assertTrue(String.format("mandatory character(%s) absent", ch), actualResult.contains(ch)));

        actualResult.forEach(ch ->
            assertFalse(String.format("Character %s is alphabetic", ch), Character.isAlphabetic(ch)));

    }

}
