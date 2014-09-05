package com.aif.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import com.aif.language.sentence.AIF2NLPSentenceSplitter;
import com.aif.language.sentence.OpenNLPSentenceSplitter;
import com.aif.language.sentence.StanfordNLPSentenceSplitter;

/**
 * @Test
 * 
 *       This class gets results made by
 *       com.aif.language.sentence.AIF2NLPSentenceSplitter,
 *       com.aif.language.sentence.OpenNLPSentenceSplitter,
 *       com.aif.language.sentence.StanfordNLPSentenceSplitter classes and
 *       compares it with a standard text body. The standard must to be 100%
 *       correct and has the exact number of sentences. And if we compare our
 *       result with the standard, we will get quality output result.
 * 
 *       author oakslist
 * 
 */

public class QualityOutputResults {

	private static final String STANDARD_TEXT_PATH = "/com/aif/language/"
			+ "sentence/for_sentence_split_test.txt";

	private String stringFile = null;
	private int standardSentencesNumber = 5000; // it's number just from my head
	
	
	public static void main(String args[]) {

		// default test
		new QualityOutputResults();

		// test with different file
		new QualityOutputResults(
				ClassLoader.class.getResourceAsStream("/unitTestData/TestData/"
						+ "RU/RU_alice_in_the_wonderland.txt"), 2500);

	}

	// set standard String file.
	public QualityOutputResults() {
		try(final InputStream resourceFile = ClassLoader.class
                .getResourceAsStream(STANDARD_TEXT_PATH)) {
			checkAll(FileHelper.readAllTextFromFile(resourceFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Problem with FileHelper. File not found: " + e);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Problem with FileHelper get String File: " + e);
		}
	}

	// set user standard file
	public QualityOutputResults(InputStream inputStream,
			int standardSentencesNumber) {
		this.standardSentencesNumber = standardSentencesNumber;
		try {
			stringFile = FileHelper.readAllTextFromFile(inputStream);
			checkAll(stringFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Problem with FileHelper. File not found: " + e);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Problem with FileHelper get String File: " + e);
		}

	}

	/* default getters and setters */

	public String getStringFile() {
		return stringFile;
	}

	public void setStringFile(String stringFile) {
		this.stringFile = stringFile;
	}

	public int getStandardSentencesNumber() {
		return standardSentencesNumber;
	}

	public void setStandardSentencesNumber(int standardSentencesNumber) {
		this.standardSentencesNumber = standardSentencesNumber;
	}

	/* private methods */

	private void checkAll(String stringFile) {
		// show all text in terminal
		// System.out.println(stringFile);

		// get all results:
		int aifResult = getAifResult(stringFile);
		int openNlpResult = getOpenNlpResult(stringFile);
		int stanfordResult = getStanfordResult(stringFile);

		System.out.println("AifSplitter's sentences number: " + aifResult
				+ " comp: " + compareWithStandard(aifResult) + "%");
		System.out.println("openNlpSplitter's sentences number: "
				+ openNlpResult + " comp: "
				+ compareWithStandard(openNlpResult) + "%");
		System.out.println("stanfordSplitter's sentences number: "
				+ stanfordResult + " comp: "
				+ compareWithStandard(stanfordResult) + "%");

		// TODO count result number
		System.out.println("standard sentences number: "
				+ standardSentencesNumber + "\n");
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
		InputStream modelResource = ClassLoader.class
				.getResourceAsStream(MODEL_RESOURCE_PATH);

		OpenNLPSentenceSplitter openNlpSplitter;
		try {
			openNlpSplitter = new OpenNLPSentenceSplitter(modelResource);
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

		StringReader stringReader = new StringReader(stringFile);
		List<String> stanfordResult = stanfordSplitter.split(stringReader);

		return stanfordResult.size();
	}
	
	private int compareWithStandard(int number) {
		return (int) (number * 100) / this.standardSentencesNumber;
	}

}
