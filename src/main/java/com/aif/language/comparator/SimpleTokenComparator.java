package com.aif.language.comparator;

import com.aif.language.comparator.ITokenComparator;

import java.util.*;


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