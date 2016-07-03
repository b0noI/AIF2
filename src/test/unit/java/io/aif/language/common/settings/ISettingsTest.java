package io.aif.language.common.settings;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class ISettingsTest {

    @Test(groups = "unit-tests")
    public void testGetVersion() throws Exception {
        // input parameter

        // mocks

        // expected result
        final String expectedResult = "2.0.0-Beta2";

        // execution test
        final String actualResult = ISettings.SETTINGS.getVersion();

        // asserts
        assertEquals(actualResult, expectedResult);

        // verify

    }
}