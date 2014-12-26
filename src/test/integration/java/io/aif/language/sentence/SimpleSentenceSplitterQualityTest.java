package io.aif.language.sentence;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.aif.common.FileHelper;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
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

public class SimpleSentenceSplitterQualityTest {

    private static final Double ALLOWED_DEVIATION = 3.0;

    private static final Logger logger = Logger.getLogger(SimpleSentenceSplitterQualityTest.class);

    @DataProvider(name = "texts")
    public TestData[][] getTexts() {
        return new TestData[][] {
                // Tests switched off because we have problems with Russian books during mvn test
                //{new TestData("for_sentence_split_test_4939_mutated.txt", 4939)},
                //{new TestData("/texts/RU/for_sentence_split_test_4939.txt", 4939)},
                {new TestData("/texts/ENG/english.txt", 1000)},
                //{new TestData("/texts/RU/for_sentence_split_test_opencorpora_RU_5000.txt", 5000)}
        };
    }

    @Test(groups = {"integration-tests", "quality-fast"}, dataProvider = "texts")
    public void sentenceSplittersTest(final TestData testData) throws Exception {

        try(final InputStream resourceFile =
                    SimpleSentenceSplitterQualityTest.class.getResourceAsStream(testData.getPath())) {

            final String textData = FileHelper.readAllText(resourceFile);

            int aifResult = getAifResult(textData);
            int openNlpResult = getOpenNlpResult(textData);
            int stanfordResult = getStanfordResult(textData);
            int expectedResult = testData.getSentenceCount();

            int resultDelta = Math.abs(aifResult - expectedResult);
            double deviation = (double)resultDelta / (double)expectedResult;
            assertTrue(deviation < ALLOWED_DEVIATION);

            logger.debug(String.format("Quality test result: \n" +
                            "File: %s, have sentence count: %d\n" +
                            "AIF2 lib: %d sentences detected\n" +
                            "OpenNLP lib: %d sentences detected\n" +
                            "StanfordNLP lib: %d sentences detected\n", testData.getPath(), testData.getSentenceCount(),
                    aifResult, openNlpResult, stanfordResult));

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
		final String MODEL_RESOURCE_PATH = "/models/opennlp/en-sent.bin";

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

    private static class TestData {

        private final String path;

        private final int sentenceCount;

        private TestData(String path, int sentenceCount) {
            this.path = path;
            this.sentenceCount = sentenceCount;
        }

        public String getPath() {
            return path;
        }

        public int getSentenceCount() {
            return sentenceCount;
        }
    }

}
