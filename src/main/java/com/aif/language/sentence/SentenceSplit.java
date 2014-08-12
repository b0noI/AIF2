package com.aif.language.sentence;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andriikr on 14/06/2014.
 */
public class SentenceSplit implements ISentenceSplit {
    private final static String REGEX = "^.*[\\.!\\?]$";

    @Override
    public List<List<String>> parseSentences(List<String> tokens) {
        final List<Integer> listOfPositions = tokens.parallelStream()
                .map(token -> token.matches(REGEX) ? tokens.indexOf(token) : 0)
                .filter(index -> index != 0)
                .collect(Collectors.toList());
        // tokens.subList(fromIndex, toIndex) /* -- indexes taken from listOfPositions */

        List<List<String>> sentences = null;

        return sentences;
    }
}
