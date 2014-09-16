package com.aif.language.sentence;

import com.aif.common.FileHelper;
import com.aif.language.common.ISplitter;
import com.aif.language.token.TokenSplitter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.*;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class SentenceSplitterCharactersExtractorQualityTest {

    @DataProvider(name = "path_provider")
    private static String[][] pathProvider() {
       return new String[][]{
               {"46800-0.txt"},
               // {"for_sentence_split_test_4939.txt"},                 // not working on CI due to Russian text
               // {"for_sentence_split_test_opencorpora_RU_5000.txt"},
               // {"RU_alice_in_the_wonderland.txt"}                    // not working on CI due to Russian text
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
                '.', ',', '(', ')'
        });

        // creating test instance
        final ISentenceSeparatorExtractor testInstance = ISentenceSeparatorExtractor.Type.PROBABILITY.getInstance();

        // execution test
        final List<Character> actualResult = testInstance.extract(inputToken).get();

        // result assert

        long correct  = actualResult
                .stream()
                .filter(ch -> expectedResult.contains(ch))
                .count();
        double result = (double)correct / (double)expectedResult.size();
        assertTrue(String.format("result is: %f", result), result > 0.9);

        mandatoryCharacters.forEach(ch ->
            assertTrue(String.format("mandatory character(%s) absent", ch), actualResult.contains(ch)));

        actualResult.forEach(ch ->
            assertFalse(String.format("Character %s is alphabetic", ch), Character.isAlphabetic(ch)));

    }

    // will be removed, used for collection result for article
    @Test (groups = "experimental")
    public void test1() throws Exception {
        String text;

        try(InputStream modelResource = SentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream("46800-0_mutated.txt")) {
            text = FileHelper.readAllText(modelResource);
        }

        StanfordNLPSentenceSplitter stanfordNLPSentenceSplitter = new StanfordNLPSentenceSplitter();

        List<String> sentences = stanfordNLPSentenceSplitter.split(text);
        assertTrue(sentences.size() > 0);

        try(InputStream modelResource = ClassLoader.class.getResourceAsStream("/models/opennlp/en-sent.bin")) {
            ISplitter<String, String> splitter =
                    new OpenNLPSentenceSplitter(modelResource);

            List<String> actualResult = splitter.split(text);

            assertTrue(actualResult.size() > 0);
        }

        AIF2NLPSentenceSplitter aif2NLPSentenceSplitter = new AIF2NLPSentenceSplitter();
        List<List<String>> sentences2 = aif2NLPSentenceSplitter.split(text);
        assertTrue(sentences2.size() > 0);
    }

}
