package com.aif.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.aif.language.sentence.AIF2NLPSentenceSplitter;
import com.aif.language.sentence.OpenNLPSentenceSplitter;
import com.aif.language.sentence.StanfordNLPSentenceSplitter;
import org.testng.annotations.Test;
import static junit.framework.Assert.assertTrue;

/**
 * @Test
 * 
 *       This class gets results made by
 *       AIF2NLPSentenceSplitter,
 *       OpenNLPSentenceSplitter,
 *       StanfordNLPSentenceSplitter classes and
 *       compares it with a standard text body. The standard must to be 100%
 *       correct and has the exact number of sentences. And if we compare our
 *       result with the standard, we will get quality output result.
 * 
 *       author oakslist
 * 
 */

public class SentenceSplitterQualityTest {

	private static final String STANDARD_TEXT_PATH = "/com/aif/language/"
			+ "sentence/for_sentence_split_test.txt";


    @Test(groups = "functional-fast")
    public void russianSentenceSplittersTest() {
        final int ethalonSentenceCount = 3015;
        final String textPath = "/unitTestData/TestData/RU/RU_alice_in_the_wonderland.txt";

        try(final InputStream resourceFile =
                    ClassLoader.class.getResourceAsStream(textPath)) {

            final String textData = FileHelper.readAllTextFromFile(resourceFile);

            int aifResult = getAifResult(textData);
            int openNlpResult = getOpenNlpResult(textData);
            int stanfordResult = getStanfordResult(textData);

            assertTrue(ethalonSentenceCount <= aifResult);

            System.out.println(String.format("Quality test result: \n" +
                    "AIF2 lib: %d sentences detected\n" +
                    "OpenNLP lib: %d sentences detecded\n" +
                    "StanfordNLP lib: %d sentences detected", aifResult, openNlpResult, stanfordResult));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	// get the AIF's splitter result:
	private int getAifResult(String stringFile) {
		AIF2NLPSentenceSplitter aifSplitter = new AIF2NLPSentenceSplitter();
		List<List<String>> aifResult = aifSplitter.split(stringFile);
		return aifResult.size();
	}

	// get the OpenNLP's splitter result. Return -1 if we have some problems
	// with IOException when we create modelResource otherwise return number of
	// sentences.
	private int getOpenNlpResult(String stringFile) {
		final String MODEL_RESOURCE_PATH = "/opennlp-models/en-sent.bin";

		try(InputStream modelResource = ClassLoader.class.getResourceAsStream(MODEL_RESOURCE_PATH)) {
            OpenNLPSentenceSplitter openNlpSplitter = new OpenNLPSentenceSplitter(modelResource);
			List<String> openNlpResult = openNlpSplitter.split(stringFile);

			return openNlpResult.size();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Problem with openNLP splitter: " + e);
		}

		return -1;
	}

	// get the Stanford's splitter result:
	private int getStanfordResult(String stringFile) {
		StanfordNLPSentenceSplitter stanfordSplitter = new StanfordNLPSentenceSplitter();

		List<String> stanfordResult = stanfordSplitter.split(stringFile);

		return stanfordResult.size();
	}

}
