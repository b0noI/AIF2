package com.aif.language.sentence;

import com.aif.language.common.ISplitter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;


public class OpenNLPSentenceSplitterTest {

    private static final String MODEL_RESOURCE_PATH = "/opennlp-models/en-sent.bin";

    @Test
    public void testSplit() throws IOException{
        final String inputString = " First sentence. Second sentence.";
        final List<String> expectedResult = Arrays.asList("First sentence.", "Second sentence.");

        try(InputStream modelResource = ClassLoader.class.getResourceAsStream(MODEL_RESOURCE_PATH)) {
            ISplitter<String, String> splitter =
                    new OpenNLPSentenceSplitter(modelResource);

            List<String> actualResult = splitter.split(inputString);

            assertEquals(expectedResult, actualResult);
        }
    }

}
