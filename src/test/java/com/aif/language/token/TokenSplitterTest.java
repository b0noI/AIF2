package com.aif.language.token;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by admin on 20.08.14.
 */
public class TokenSplitterTest {

    private Path path_to_file;

    @Test
    public void shouldGetTokensFromBook_AliceInTheWonderlends_RU() {

        try {

            path_to_file = Paths.get(getClass().getResource("/TestData/RU/alice_in_the_wonderland.txt").toURI());

            final TokenSplitter tokenSplitter = new TokenSplitter(ITokenSeparatorExtractor.Type.PREDEFINED.getInstance());

            List<String> output = tokenSplitter.split(textFromFileToString(path_to_file));

            System.out.println(output.size());
            System.out.println(output.get(output.size()-1));

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
