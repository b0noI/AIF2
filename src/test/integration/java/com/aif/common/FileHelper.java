package com.aif.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileHelper {

    public static String readAllText(final InputStream inputStream) throws IOException {
        try(final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            final StringBuffer buff = new StringBuffer();

            String line = null;

            while((line = reader.readLine()) != null) {
                buff.append(line + System.lineSeparator());
            }

            return buff.toString();
        } catch (NullPointerException e) {
            throw e;
        }
    }

}
