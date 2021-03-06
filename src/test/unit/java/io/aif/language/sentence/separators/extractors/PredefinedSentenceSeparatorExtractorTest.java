package io.aif.language.sentence.separators.extractors;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class PredefinedSentenceSeparatorExtractorTest {

  @Test(groups = "unit-tests")
  public void testExtract() throws Exception {
    // input arguments
    final List<String> inputTokens = null;

    // mocks

    // expected results
    final Optional<List<Character>> expectedResult = Optional.of(Arrays.asList('.', '!', '?', '(',
        ')', '[', ']', '{', '}', ';', '\'', '\"'));

    // creating test instance
    final ISeparatorExtractor sentenceSeparatorExtractor =
        ISeparatorExtractor.Type.PREDEFINED.getInstance();

    // execution test
    final Optional<List<Character>> actualResult = sentenceSeparatorExtractor.extract(inputTokens);

    // result assert
    assertEquals(expectedResult, actualResult);

    // mocks verify
  }

}