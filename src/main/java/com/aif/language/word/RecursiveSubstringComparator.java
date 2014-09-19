package com.aif.language.word;

import com.aif.language.common.StringHelper;

/**
 * - Find the biggest substring in the two strings and calculates it's length.
 * - Recursively on the left and right continue finding substrings and get the length.
 * - Once the above is completed sum all the lengths of substrings, multiply by 2 and divide by the sum of the lengths
 *      of the 2 strings
 */

public class RecursiveSubstringComparator implements ITokenComparator {

    @Override
    public Double compare(final String t1, final String t2) {
        return (double) (sumOfLongestCommonSubstrings(t1, t2) * 2) / (t1.length() + t2.length());
    }

    private int sumOfLongestCommonSubstrings(final String t1, final String t2) {
        if (t1.length() == 0 || t2.length() == 0)
            return 0;

        String longestCommonSubstring = StringHelper.findLongestCommonSubstring(t1, t2);
        if (longestCommonSubstring.length() == 0)
            return 0;

        String t1Parts[] = t1.split(longestCommonSubstring, 2);
        String t2Parts[] = t2.split(longestCommonSubstring, 2);

        return longestCommonSubstring.length() +
                sumOfLongestCommonSubstrings(t1Parts[0], t2Parts[0]) +
                sumOfLongestCommonSubstrings(t1Parts[1], t2Parts[1]);
    }
}
