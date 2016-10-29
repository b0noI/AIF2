package io.aif.language.sentence.separators.extractors;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class NonAlphabeticCharactersExtractorTest {

  private final NonAlphabeticCharactersExtractor extractor = new NonAlphabeticCharactersExtractor();

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test(groups = "unit-tests")
  public void testExtract() {
    List<String> testList = new ArrayList<>();
    List<Character> result = extractor.extract(testList).get();
    assertTrue(result.isEmpty());
    testList.add("1");
    result = extractor.extract(testList).get();
    assertTrue(result.isEmpty());
    testList.add("test*1");
    result = extractor.extract(testList).get();
    assertEquals(1, result.size());
    assertTrue(result.get(0) == '*');
    testList.add("test&&");
    result = extractor.extract(testList).get();
    assertEquals(3, result.size());
    assertTrue(result.get(0) == '*');
    assertTrue(result.get(1) == '&');
  }
}
