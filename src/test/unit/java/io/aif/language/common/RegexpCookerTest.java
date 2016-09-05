package io.aif.language.common;

import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

public class RegexpCookerTest {

  @Test(groups = "unit-tests")
  public void testPrepareRegexp() throws Exception {
    // given
    final RegexpCooker regexpCooker = new RegexpCooker();

    // when
    final String actualRegexp = regexpCooker.prepareRegexp(asList('\n', '\t', ' '));

    // then
    assertEquals(actualRegexp, "[\\\n\\\t\\ ]+");
  }
}