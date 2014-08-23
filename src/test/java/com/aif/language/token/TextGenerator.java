package com.aif.language.token;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by admin on 21.08.14.
 */
public class TextGenerator {

    private String baseToken;
    private char[] tokenElements;
    final private char[] elements = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйцукенгшщзхъфывапролджэячсмитьбю".toCharArray();
    private char separator;
    private int filesCount;
    private Path location;
    private int step;
    private int tokensInOneLine;
    private int initialTekensNumber;
    private String lenguage;

    public TextGenerator() {
        this.baseToken = "duck";
        this.tokenElements = baseToken.toCharArray();
        this.separator = ' ';
        this.filesCount = 1;
        this.step = 1;
        this.lenguage = "";
        this.tokensInOneLine = 10;
        this.initialTekensNumber = 10;
        try {
            this.location = Paths.get(getClass().getResource("/TestData").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public TextGenerator setBaseToken(String baseToken) {
        this.baseToken = baseToken;
        tokenElements = baseToken.toCharArray();
        return this;
    }

    public TextGenerator setSeparator(char separator) {
        this.separator = separator;
        return this;
    }

    public TextGenerator setFilesCount(int filesCount) {
        this.filesCount = filesCount;
        return this;
    }

    public TextGenerator setStep(int step) {
        this.step = step;
        return this;
    }

    public TextGenerator setLocation(Path location) {
        this.location = location;
        return this;
    }

    public TextGenerator setLenguage(String lenguage) {
        this.lenguage = lenguage;
        return this;
    }

    public TextGenerator setTokensInLine(int tokensInLine) {
        this.tokensInOneLine = tokensInLine;
        return this;
    }

    public TextGenerator setInitialTokensInFile(int tokensInFile) {
        this.initialTekensNumber = tokensInFile;
        return this;
    }

    public void generate() {

        int tokensInFile = initialTekensNumber;
        int currentTokensInLine = 0;

        if(!location.toFile().exists())
            location.toFile().mkdirs();


        for(int i = filesCount; i > 0; i--) {

            String fileName = lenguage+"_"+tokensInFile+"_"+ baseToken +".txt";

            try(BufferedWriter bfw = new BufferedWriter(new FileWriter(Paths.get(location.toString()+"/"+fileName).toFile()))) {

                for(int j = tokensInFile; j > 0; j--) {

                    bfw.write(randomizeByBaseToken());
                    currentTokensInLine++;

                    if(currentTokensInLine == tokensInOneLine) {
                        bfw.newLine();
                        currentTokensInLine = 0;
                    }else {
                        bfw.write(separator);
                    }
                }

                tokensInFile += step;

            }catch (IOException e) {
                    e.printStackTrace();
            }
        }
    }

    private String randomizeByBaseToken() {

        StringBuffer outToken = new StringBuffer();

        Random random = new Random();

        for(int i = 0; i < random.nextInt(15); i++) {

            outToken.append(elements[random.nextInt(elements.length-1)]);

        }

        return outToken.toString();
    }
}
