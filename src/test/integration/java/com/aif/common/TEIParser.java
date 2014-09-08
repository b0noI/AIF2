package com.aif.common;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


public class TEIParser implements ICorporaParser {
    private final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    private int sentenceCount = 0;

    @Override
    public String toPlainText(InputStream xmlInput) {
        try {

            SAXParser parser = parserFactory.newSAXParser();
            TEIHandler handler = new TEIHandler();

            parser.parse(xmlInput, handler);

            sentenceCount = handler.getSentenceCount();

            return handler.getResult();
        } catch (ParserConfigurationException|SAXException|IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int getSentenceCount() {
        return sentenceCount;
    }

    private static final class TEIHandler extends DefaultHandler {
        private static final String SENTENCE_TAG = "s";
        private static final String HEADING_TAG = "head";
        private static final String WORD_TAG = "w";
        private static final String PARAGRAPH_TAG = "p";
        private static final List<String> TEXT_ELEMENTS = Arrays.asList("w", "c", "seg");
        private int sentenceCount = 0;

        private final StringBuilder resultBuilder = new StringBuilder();

        private boolean textElement = false;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(TEXT_ELEMENTS.contains(qName.toLowerCase()))
                textElement = true;

            if(qName.equalsIgnoreCase(HEADING_TAG))
                resultBuilder.append("\n\n");

            if(qName.equalsIgnoreCase(PARAGRAPH_TAG))
                resultBuilder.append('\n');
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equalsIgnoreCase(SENTENCE_TAG)) {
                resultBuilder.append(' ');
                sentenceCount++;
            }

            if(qName.equalsIgnoreCase(HEADING_TAG))
                resultBuilder.append("\n\n");

            textElement = false;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if(textElement) {

                String data = new String(ch, start, length).replaceAll("\n", " ");
                if(!data.matches("\\s+"))
                    resultBuilder.append(data);
            }
        }

        public String getResult() {

            return resultBuilder.toString();
        }

        public int getSentenceCount() {
            return sentenceCount;
        }

    }
}
