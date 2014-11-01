package io.aif.language.common;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;


public class StringHelper {

    public static String findLongestCommonSubstring(final String t1, final String t2) {
        String s1 = t1.toLowerCase();
        String s2 = t2.toLowerCase();

        final int shortestWordLength = Math.min(t1.length(), t2.length());
        Set<String> s1SubWords;
        Set<String> s2SubWords;
        String subWord = "";

        int subWordLength = shortestWordLength;
        while (subWordLength > 0) {
            // TODO: Potential candidate to parallelise
            s1SubWords = generateSubWords(s1, subWordLength);
            s2SubWords = generateSubWords(s2, subWordLength);

            subWord = searchForCommonString(s1SubWords, s2SubWords);
            if (!subWord.isEmpty())
                break;

            subWordLength--;
        }
        return subWord;
    }

    public static String searchForCommonString(Set<String> s1, Set<String> s2) {
        // TODO: Dealing with multiple common strings.
        String matched = "";
        for (String s1Word : s1)
            if (s2.contains(s1Word)) {
                matched = s1Word;
                break;
            }
        return matched;
    }

    public static Set<String> generateSubWords(String word, int length) {
        if (length > word.length())
            throw new IllegalArgumentException(
                    "The word '" + word + "' length cannot be less than the length " + length
                    + " of the words to be generated.");

        // LinkedHashSet maintains the order. if there are multiple common substrings the left most should match first.
        Set<String> generatedWords = new LinkedHashSet<>();
        for (int i = 0; i <= (word.length() - length); i++)
            generatedWords.add(word.substring(i, length + i));

        return generatedWords;
    }
}
