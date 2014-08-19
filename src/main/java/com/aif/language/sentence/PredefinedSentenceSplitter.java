package com.aif.language.sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vsk on 8/19/14.
 */
public class PredefinedSentenceSplitter implements ISentenceSplitter {
    private final static String REGEX = "^.*[\\.!\\?]$";

    @Override
    public List<List<String>> parseSentences(List<String> tokens) {
        final List<Boolean> listOfPositions = PredefinedSentenceSplitter.mapToBooleans(tokens);

        final List<List<String>> sentances = new ArrayList<>();

        while (listOfPositions.contains(true)) {
            int index = listOfPositions.indexOf(true);
            final List<String> sentance = tokens.subList(0, index + 1);
            sentances.add(sentance);

            int newSize = tokens.size() - sentance.size();
            while (tokens.size() != newSize) {
                tokens.remove(0);
                listOfPositions.remove(0);
            }

        }

        if (tokens.size() > 0) {
            sentances.add(tokens);
        }

        List<List<String>> sentences = null;

        return sentences;
    }

    private static List<Boolean> mapToBooleans(final List<String> tokens) {
        return tokens.parallelStream()
                .map(token -> token.matches(REGEX))
                .collect(Collectors.toList());
    }

}
