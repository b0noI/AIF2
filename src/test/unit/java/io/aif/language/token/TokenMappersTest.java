package io.aif.language.token;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TokenMappersTest {

  @Test(groups = "unit-tests")
  public void testRemoveMultipleEndCharacters() throws Exception {
    // input arguments
    final String inputToken = "Token....";

    // mocks

    // expected results
    final String expectedToken = "Token.";

    // creating test instance

    // execution test
    final String actualResult = TokenMappers.removeMultipleEndCharacters(inputToken);

    // result assert
    assertEquals(actualResult, expectedToken);

    // mocks verify
  }

}