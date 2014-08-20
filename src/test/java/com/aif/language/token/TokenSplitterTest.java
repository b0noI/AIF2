package com.aif.language.token;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by admin on 20.08.14.
 */
public class TokenSplitterTest {

    private Path path_to_file;

    @Test
    public void should_get_tokens_from_text_file_with_space_in_the_begining_using_PREDIFINED_separator() {

        try {

            path_to_file = Paths.get(getClass().getResource("/TestData/RU/RU_text_with_space_begining.txt").toURI());

            //Expected results:

            final String lastToken = "токенов.";
            final String firstToken = "-";
            final long numberOfTokens = 11;

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


    private String textFromFileToString(final Path pathToFile) {

        try(BufferedReader reader = Files.newBufferedReader(pathToFile)) {

            StringBuffer buff = new StringBuffer();

            String line = null;

            while((line = reader.readLine()) != null)
                buff.append(line);

            return buff.toString();

        }catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
