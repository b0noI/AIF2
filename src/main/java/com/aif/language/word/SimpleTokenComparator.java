package com.aif.language.word;

import java.util.*;
import java.util.stream.Collectors;

/**
 * - Find the biggest substring in the two strings and calculates it's length.
 * - Recursively on the left and right continue finding substrings and get the length.
 * - Once the above is completed sum all the lengths of substrings, multiply by 2 and divide by the sum of the lengths
 *      of the 2 strings
 */

public class SimpleTokenComparator implements ITokenComparator {

    @Override
    public Double compare(String t1, String t2) {
        int commonCharCount = findNumberofCommonChars(t1, t2);
        return proximity(commonCharCount, t1.length(), t2.length());
    }

    private int findNumberofCommonChars(String t1, String t2) {
        t1 = t1.toLowerCase();
        t2 = t2.toLowerCase();
        int commonCharCount = 0;
        Map<Character, Integer> t2Map = new HashMap<>();
        for (char c : t2.toCharArray()) {
            int count = (t2Map.containsKey(c)) ? t2Map.get(c) + 1 : 1;
            t2Map.put(c, count);
        }

        for (char c : t1.toCharArray()) {
            if (t2Map.containsKey(c)) {
                commonCharCount += t2Map.get(c);
                t2Map.remove(c);
            }
        }
        return commonCharCount;
    }

    private Double proximity(int commonCharacterCount, int t1Len, int t2Len) {
        return (commonCharacterCount * 2) / ((double)t1Len + (double)t2Len);
    }
}