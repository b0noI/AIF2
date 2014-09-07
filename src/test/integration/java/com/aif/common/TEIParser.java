package com.aif.common;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;


public class TEIParser implements ICorporaParser {
    private final SAXParserFactory parserFactory = SAXParserFactory.newInstance();

    @Override
    public String toPlainText(InputStream xmlInput) {
        try {

            SAXParser parser = parserFactory.newSAXParser();
            TEIHandler handler = new TEIHandler();

            parser.parse(xmlInput, handler);

            return handler.getResult();
        } catch (ParserConfigurationException|SAXException|IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static final class TEIHandler extends DefaultHandler {
        private final StringBuilder resultBuilder = new StringBuilder();

        public String getResult() {

            return resultBuilder.toString();
        }

    }
}
