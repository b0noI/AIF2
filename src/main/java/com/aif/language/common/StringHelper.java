package com.aif.language.common;

import java.util.LinkedHashSet;
import java.util.Set;


public class StringHelper {

    public static String findBiggestSubstring(final String t1, final String t2) {
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
            if (subWord != "")
                break;

            subWordLength--;
        }
        return subWord;
    }

    public static String searchForCommonString(Set<String> s1, Set<String> s2) {
        // FIXME: The method returns an empty string if no matching words are found.
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

        Set<String> generatedWords = new LinkedHashSet<>();
        for (int i = 0; i <= (word.length() - length); i++)
            generatedWords.add(word.substring(i, length + i));

        return generatedWords;
    }
}
