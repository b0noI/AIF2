package io.aif.language.common;

import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.testng.Assert.*;

public class RegexpCookerTest {

    @Test(groups = "unit-tests")
    public void testPrepareRegexp() throws Exception {
        // given
        RegexpCooker regexpCooker = new RegexpCooker();

        // when
        String actualRegexp = regexpCooker.prepareRegexp(asList('\n', '\t', ' '));

        // then
        assertEquals(actualRegexp, "[\\\n\\\t\\ ]+");
    }
}