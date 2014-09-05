package com.aif.language.token;


import org.testng.annotations.BeforeMethod;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import com.aif.language.common.ISplitter;
import com.aif.language.common.RegexpCooker;
import com.aif.language.sentence.ISentenceSeparatorExtractor;
import com.aif.language.sentence.SentenceSplitter;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.testng.annotations.Test;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class TokenSplitterTest {

    @Test(groups = "unit-tests")
    public void testExtract() throws Exception {
        final String inputText = "test1 test2\ntest3";
        final List<String> expectedResult = new ArrayList<>();
        expectedResult.add("test1");
        expectedResult.add("test2");
        expectedResult.add("test3");

        final ISplitter<String, String> tokenSeparatorExtractor = new TokenSplitter();
        final List<String> actualResult = tokenSeparatorExtractor.split(inputText);

        assertEquals(expectedResult, actualResult);
    }

    @Test(groups = "unit-tests")
    public void testConstructor() throws Exception {
        final ITokenSeparatorExtractor mockTokenSeparatorExtractor = mock(ITokenSeparatorExtractor.class);

        new TokenSplitter(mockTokenSeparatorExtractor);
    }

    @Test(groups = "unit-tests")
    public void testSplitWhenSplittersNotFound() throws Exception {

        // input parameter
        final String inputText = "token1 token2";

        // mocks
        final Optional<List<Character>> mockSplitCharacters = Optional.ofNullable(null);

        final ITokenSeparatorExtractor mockTokenSeparatorExtractor = mock(ITokenSeparatorExtractor.class);
        when(mockTokenSeparatorExtractor.extract(eq(inputText))).thenReturn(mockSplitCharacters);

        // expected result
        final List<String> expectedResult = Arrays.asList(inputText);

        // creating instances
        final ISplitter<String, String> tokenSplitter = new TokenSplitter(mockTokenSeparatorExtractor);

        // execution test
        final List<String> actualResult = tokenSplitter.split(inputText);

        // asserts
        assertEquals(expectedResult, actualResult);

        // verify
        verify(mockTokenSeparatorExtractor, times(1)).extract(inputText);
    }

    @Test(groups = "unit-tests")
    public void testSplitWhenSplittersFound() throws Exception {

        // input parameter
        final String inputText = "token1  token2";

        // mocks
        final Optional<List<Character>> mockOptionalsSplitCharacters = Optional.of(Arrays.asList(' ', '\n'));

        final ITokenSeparatorExtractor mockTokenSeparatorExtractor = mock(ITokenSeparatorExtractor.class);
        when(mockTokenSeparatorExtractor.extract(eq(inputText))).thenReturn(mockOptionalsSplitCharacters);

        final RegexpCooker mockRegexpCooker = mock(RegexpCooker.class);
        when(mockRegexpCooker.prepareRegexp(eq(Arrays.asList(' ', '\n')))).thenReturn("[ \n]+");

        // expected result
        final List<String> expectedResult = Arrays.asList("token1", "token2");

        // creating instances
        final ISplitter<String, String> tokenSplitter = new TokenSplitter(mockTokenSeparatorExtractor, mockRegexpCooker);

        // execution test
        final List<String> actualResult = tokenSplitter.split(inputText);

        // asserts
        assertEquals(expectedResult, actualResult);

        // verify
        verify(mockTokenSeparatorExtractor, times(1)).extract(inputText);
        verify(mockRegexpCooker, times(1)).prepareRegexp(Arrays.asList(' ', '\n'));
    }

    @Test(groups = "unit-tests")
    public void testFilterIncorrectTokens() throws Exception {
        // input parameter
        final List<String> inputTokens = Arrays.asList("token1", "", "token2");

        // mocks

        // expected result
        final List<String> expectedResult = Arrays.asList("token1", "token2");

        // creating instances

        // execution test
        final List<String> actualResult = TokenSplitter.filterIncorrectTokens(inputTokens);

        // asserts
        assertEquals(expectedResult, actualResult);

        // verify
    }

}
