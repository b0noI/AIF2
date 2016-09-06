package io.aif.language.sentence;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import io.aif.language.common.ISplitter;
import jdk.nashorn.internal.ir.annotations.Ignore;

import static org.testng.AssertJUnit.assertEquals;

public class StanfordNLPSimpleSentenceSplitterTest {

  @Ignore
  @Test(groups = "help-test")
  public void testSplit() {

    final String inputString = " First sentence. Second sentence.";
    final List<String> expectedResult = Arrays.asList("First sentence .", "Second sentence .");

    ISplitter<String, String> stanfordSplitter = new StanfordNLPSentenceSplitter();

    List<String> actualResult = stanfordSplitter.split(inputString);

    assertEquals(expectedResult, actualResult);

  }

}