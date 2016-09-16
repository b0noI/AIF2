package io.aif.language.common.settings;

import com.google.inject.Guice;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ISettingsTest {

  private ISettings settings;

  @BeforeClass
  public void setup(){
    settings = Guice.createInjector(new SettingsModule()).getInstance(ISettings.class);
  }


  @Test(groups = "unit-tests")
  public void testGetVersion() throws Exception {
    // input parameter

    // mocks

    // expected result
    final String expectedResult = "2.0.0-Beta2";

    // execution test
    final String actualResult = settings.getVersion();

    // asserts
    assertEquals(actualResult, expectedResult);

    // verify

  }

  @Test(groups = "unit-tests")
  public void testPredefinedSeparators() throws Exception {
    // input parameter

    // mocks

    // expected result
    final String expectedResult = "\r\n \t\u000B\u000C\u0085\u00A0\u1680\u2000\u2001\u2002\u2003" +
        "\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u2028\u2029\u202F\u205F\u3000\u180E\u200B" +
        "\u200C\u200D\u2060\uFEFF";

    // execution test
    final String actualResult = settings.predefinedSeparators();

    // asserts
    assertEquals(actualResult, expectedResult);

    // verify

  }
}