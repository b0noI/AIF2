package com.aif.language.sentence;

//import com.aif.language.sentence.splitters.SentenceSplitter;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class SentenceIteratorTest {

    private static final List<String> INPUT_TOKENS = Arrays.asList(new String[]{
            "token1",
            "token2",
            "token3",
            "token4"
    });

    private static final List<Boolean> INPUT_END_POINTS = Arrays.asList(new Boolean[]{
            false,
            true,
            true,
            true
    });

    @Test(groups = "unit-tests")
    public void testHasNext() throws Exception {
        // input arguments

        // mocks

        // expected results

        // creating test instance
//        final SentenceSplitter.SentenceIterator sentenceIterator = new SentenceSplitter.SentenceIterator(INPUT_TOKENS, INPUT_END_POINTS);
//
//        // execution test
//        assertTrue(sentenceIterator.hasNext());
//        sentenceIterator.next();
//        assertTrue(sentenceIterator.hasNext());
//        sentenceIterator.next();
//        assertTrue(sentenceIterator.hasNext());
//        sentenceIterator.next();
//        assertFalse(sentenceIterator.hasNext());
//
//        // result assert
//
//        // mocks verify
    }

    @Test(groups = "unit-tests")
     public void testNext() throws Exception {
        // input arguments

        // mocks

        // expected results
        final List<String> expectedSentence1 = Arrays.asList(new String[]{
                "token1",
                "token2"
        });
        final List<String> expectedSentence2 = Arrays.asList(new String[]{
                "token3",
        });
        final List<String> expectedSentence3 = Arrays.asList(new String[]{
                "token4",
        });

        // creating test instance
//        final SentenceSplitter.SentenceIterator sentenceIterator = new SentenceSplitter.SentenceIterator(INPUT_TOKENS, INPUT_END_POINTS);
//
//        // execution test
//        final List<String> actualSentence1 = sentenceIterator.next();
//        final List<String> actualSentence2 = sentenceIterator.next();
//        final List<String> actualSentence3 = sentenceIterator.next();
//
//        // result assert
//        assertEquals(expectedSentence1, actualSentence1);
//        assertEquals(expectedSentence2, actualSentence2);
//        assertEquals(expectedSentence3, actualSentence3);
//
//        // mocks verify
    }

    @Test(groups = "unit-tests")
    public void testHasNextWhenSentencesSzieZero() throws Exception {
        // input arguments
        final List<String> inputTokens = Arrays.asList(new String[0]);
        final List<Boolean> inputEndPoints = Arrays.asList(new Boolean[0]);

        // mocks

        // expected results
        final Boolean expectedResult = false;

//        // creating test instance
//        final SentenceSplitter.SentenceIterator sentenceIterator = new SentenceSplitter.SentenceIterator(inputTokens, inputEndPoints);
//
//        // execution test
//        final Boolean actualResult = sentenceIterator.hasNext();
//
//        // result assert
//        assertEquals(expectedResult, actualResult);
//
//        // mocks verify
    }

}