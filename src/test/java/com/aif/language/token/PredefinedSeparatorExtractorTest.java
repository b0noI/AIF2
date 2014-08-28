//package com.aif.language.token;
//
//import org.junit.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class PredefinedSeparatorExtractorTest {
//
//    @Test
//    public void testGetSeparators() throws Exception {
//        final ITokenSeparatorExtractor testClass = ITokenSeparatorExtractor.Type.PREDEFINED.getInstance();
//        final List<Character> expectedResult = Arrays.asList(new Character[]{' ', '\n'});
//
//        final List<Character> actualResult = testClass.getSeparators("");
//
//        assertEquals(expectedResult, actualResult);
//    }
//
//}