package io.aif.language.common.settings;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class ISettingsTest {

    private static final Injector INJECTOR = Guice.createInjector(new SettingsModule());

    private ISettings settingsUnderTest;

    @BeforeMethod
    public void setUp() throws Exception {
        settingsUnderTest = INJECTOR.getInstance(ISettings.class);
    }

    @Test(groups = "unit-tests")
    public void testGetVersion() throws Exception {
        // input parameter

        // mocks

        // expected result
        final String expectedResult = "1.2.0-alpha4";

        // creating instances
        final ISettings testInstance = settingsUnderTest;

        // execution test
        final String actualResult = testInstance.getVersion();

        // asserts
        assertEquals(actualResult, expectedResult);

        // verify

    }
}