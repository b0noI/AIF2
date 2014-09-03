package com.aif.language.token;

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

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;
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

    private Path path_to_file;

    //This two String are pathes for gethering statistic
    private final String pathToStatisticSet = "/Users/admin/Documents/programming/projects/AIF2/testData";
    private final String pathToStatisticResult = "/Users/admin/Documents/programming/projects/AIF2/AIF2/src/main/resources/testData/RU/Results.csv";

    @Test
    public void should_get_tokens_from_text_file_with_space_in_the_begining_using_PREDEFINED_separator() {

        try {

            path_to_file = Paths.get(getClass().getResource("/TestData/RU/RU_text_with_space_begining.txt").toURI());

            //Expected results:

            final String lastToken = "tokens.";
            final String firstToken = "-";
            final long numberOfTokens = 8;

            final TokenSplitter tokenSplitter = new TokenSplitter(ITokenSeparatorExtractor.Type.PREDEFINED.getInstance());

            final List<String> output = tokenSplitter.split(textFromFileToString(path_to_file));

            assertNotNull(output);
            assertEquals(numberOfTokens, output.size());
            assertEquals(firstToken, output.get(0));
            assertEquals(lastToken, output.get(output.size()-1));


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void should_split_new_line_words() {

        try {
            path_to_file = Paths.get(getClass().getResource("/TestData/RU/RU_text_with_new_lines.txt").toURI());

            final String lastToken = "tokenov";
            final String firstToken = "B";
            final long numberOfTokens = 9;

            final TokenSplitter tokenSplitter = new TokenSplitter(ITokenSeparatorExtractor.Type.PREDEFINED.getInstance());

            final List<String> output = tokenSplitter.split(textFromFileToString(path_to_file));

            assertNotNull(output);
            assertEquals(firstToken, output.get(0));
            assertEquals(lastToken, output.get(output.size()-1));
            assertEquals(numberOfTokens, output.size());


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void should_get_tokens_from_file_using_probability_splitter() {

        path_to_file = Paths.get("/Users/admin/Documents/programming/projects/AIF2/AIF2/src/main/resources/testData/RU/");

        TokenSplitter splitter = new TokenSplitter(ITokenSeparatorExtractor.Type.PROBABILITY.getInstance());

        List<String> tokens = splitter.split(textFromFileToString(path_to_file));

        assertNotNull(tokens);
        assertEquals(10000, tokens.size());

    }

    @Test
    @Ignore
    //TODO this is not a real test, code below used for generating statistic data for probability splitter
    public void generate_statistic_for_probability_splitter() {

        List<Path> files = getAllFilesInFolder(Paths.get(pathToStatisticSet));
        List<String> splittedText;


        TokenSplitter splitter = new TokenSplitter(ITokenSeparatorExtractor.Type.PROBABILITY.getInstance());

        for(Path path: files) {

            splittedText = splitter.split(textFromFileToString(path));

            String fileInnerName = path.toFile().getName();

            int correctElems = getTokensNumFromFileName(fileInnerName);

            saveStatisticToFile(fileInnerName, correctElems, splittedText.size());

        }
    }

    @Test
    @Ignore
    public void create_file_set_for_statistic_for_PROBABILITY_based_splitting() {

        TextGenerator tg = new TextGenerator();

        tg.setFilesCount(100)
                .setLenguage("RU")
                .setStep(10000)
                .setBaseToken("СеврюгаГрач")
                .setInitialTokensInFile(10000)
                .setLocation(Paths.get(pathToStatisticSet))
                .generate();
    }

    private List<Path> getAllFilesInFolder(Path parentPath) {

        List<Path> out = new ArrayList<>();

        String[] files = parentPath.toFile().list();

        for(String fileName: files)
            out.add(Paths.get(parentPath.toString()+"/"+fileName));


        //.DS_Store is Mac_OS hidden file, as I don't know how to ignore it I will remove it from list
        //But we need //TODO some thing with .DS_Store system file
        if(out.get(0).toFile().getName().equals(".DS_Store"))
            out.remove(0);

        return out;
    }


    private String textFromFileToString(final Path pathToFile) {

        try(BufferedReader reader = Files.newBufferedReader(pathToFile)) {

            StringBuffer buff = new StringBuffer();

            String line = null;

            while((line = reader.readLine()) != null)
                //TODO
                buff.append(line+"\n");

            return buff.toString();

        }catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private void saveStatisticToFile(String fileName, int correctTokensNum, int foundTokensNum) {

        try( BufferedWriter bw = new BufferedWriter(new FileWriter(Paths.get(pathToStatisticResult).toFile(), true))) {

            bw.write(LocalDateTime.now().toString() + "," + fileName + "," + correctTokensNum + "," + foundTokensNum);
            bw.newLine();

        }catch (IOException e) {

        }



    }
    private int getTokensNumFromFileName(String name) {
        String[] elems = name.split("_");
        return Integer.parseInt(elems[1]);
    }

}
