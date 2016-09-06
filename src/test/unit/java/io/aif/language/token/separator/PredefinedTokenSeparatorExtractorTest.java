package io.aif.language.token.separator;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.aif.language.common.settings.ISettings;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PredefinedTokenSeparatorExtractorTest {

  @Test(groups = "unit-tests")
  public void testExtract() throws Exception {
    // input parameter
    final String inputText = null;

    // mocks
    ISettings settings = mock(ISettings.class);
    when(settings.predefinedSeparators()).thenReturn("\r\n \t");

    // expected result
    final Optional<List<Character>> expectedResult =
        Optional.of(Arrays.asList('\r', '\n', ' ', '\t'));

    // creating instances
    final ITokenSeparatorExtractor testInstance = new PredefinedTokenSeparatorExtractor(settings);

    // execution test
    final Optional<List<Character>> actualResult = testInstance.extract(inputText);

    // asserts
    assertEquals(expectedResult, actualResult);

    // verify
  }

}