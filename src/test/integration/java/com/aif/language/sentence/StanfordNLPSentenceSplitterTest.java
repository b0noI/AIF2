package com.aif.language.sentence;

import com.aif.language.common.ISplitter;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class StanfordNLPSentenceSplitterTest {

    @Test
    public void testSplit() {

        final String inputString = " First sentence. Second sentence.";
        final List<String> expectedResult = Arrays.asList("First sentence .", "Second sentence .");

        ISplitter<String, String> stanfordSplitter = new StanfordNLPSentenceSplitter();

        List<String> actualResult = stanfordSplitter.split(inputString);

        assertEquals(expectedResult, actualResult);

    }

}