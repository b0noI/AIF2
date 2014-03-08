package com.aif.language.controller;

import com.aif.language.model.IText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public abstract class TextExtractor {

    protected IText extractText(BufferedReader reader) throws IOException{
        String line = null;
        while ((line = reader.readLine()) != null) {

        }
        return null;
    }

}
