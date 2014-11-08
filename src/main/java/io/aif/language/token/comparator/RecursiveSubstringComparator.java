package io.aif.language.token.comparator;

import io.aif.language.common.StringHelper;

import java.util.Arrays;

/**
 * - Find the biggest substring in the two strings and calculates it's length.
 * - Recursively on the left and right continue finding substrings and get the length.
 * - Once the above is completed sum all the lengths of substrings, multiply by 2 and divide by the sum of the lengths
 *      of the 2 strings
 */
class RecursiveSubstringComparator implements ITokenComparator {

    // TODO find correct method of filtering string Regexcharaters
    private static final Character[] CHARACTERS_FOR_FILTERING = {'?', '*', '[', ']', '(', ')', '$'};

    @Override
    public Double compare(final String t1, final String t2) {
        final String clearedT1 = clearString(t1);
        final String clearedT2 = clearString(t2);
        return (double) (sumOfLongestCommonSubstrings(clearedT1, clearedT2) * 2) / (clearedT1.length() + clearedT2.length());
    }

    private int sumOfLongestCommonSubstrings(final String t1, final String t2) {
        if (t1.length() == 0 || t2.length() == 0)
            return 0;

        final String longestCommonSubstring = StringHelper.findLongestCommonSubstring(t1, t2);
        if (longestCommonSubstring.length() == 0)
            return 0;

        final String t1Parts[] = t1.split(longestCommonSubstring, 2);
        final String t2Parts[] = t2.split(longestCommonSubstring, 2);

        if (t1Parts.length <= 1 || t2Parts.length <= 1)
            return 0;
        // TODO delete try/cath once filtering is complete
        try {
            return longestCommonSubstring.length() +
                    sumOfLongestCommonSubstrings(t1Parts[0], t2Parts[0]) +
                    sumOfLongestCommonSubstrings(t1Parts[1], t2Parts[1]);
        } catch (StackOverflowError e) {
            throw  e;
        }
    }

    private String clearString(final String token) {
        final StringBuilder sb = new StringBuilder();
        for (char ch : token.toCharArray()) {
            if (!Arrays.asList(CHARACTERS_FOR_FILTERING).contains(ch)) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

}
