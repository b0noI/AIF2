package io.aif.language.token.comparator;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;

import io.aif.language.common.StringHelper;

class CharacterDensityTokenComparator implements ITokenComparator {

  private final static int COMPARATION_LEVEL = 2;

  private static OptionalDouble computeSimilarity(
      final Map<String, Double> leftMap,
      final Map<String, Double> rightMap) {
    return leftMap.entrySet().stream().mapToDouble(pair -> {
      final double leftVal = pair.getValue();
      final double rightVal = rightMap.getOrDefault(pair.getKey(), .0);
      return 1. - (Math.abs(leftVal - rightVal) / (leftVal + rightVal));
    }).average();
  }

  private static Map<String, Double> mapStringToCharactersDensity(final String str,
                                                                  final int padding) {
    final Map<String, Double> result = new HashMap<>();
    for (int i = 0; i < str.length(); i++) {
      final char chAtI = str.charAt(i);
      for (int delta = 0; delta <= COMPARATION_LEVEL; delta++) {
        if (i - delta >= 0) {
          updateDensityFroTheCharAtPositionWithDelta(chAtI, i - delta, delta, padding, result);
        }
        if (i + delta < str.length() && delta != 0) {
          updateDensityFroTheCharAtPositionWithDelta(chAtI, i + delta, delta, padding, result);
        }
      }
    }
    return result;
  }

  private static void updateDensityFroTheCharAtPositionWithDelta(
      final char ch,
      final int position,
      final int delta,
      final int padding,
      final Map<String, Double> result) {
    final String key = "" + ch + (position + padding);
    final double currentVal = result.getOrDefault(key, .0);
    result.put(key, currentVal + (1. / (double) (delta + 1)));
  }

  @Override
  public Double compare(final String left, final String right) {
    if (left == null || right == null) return .0;
    if (right.length() < left.length()) return compare(right, left);
    if (left.isEmpty() && right.isEmpty()) return 1.;
    if (left.isEmpty() || right.isEmpty()) return .0;
    if (left.equals(right)) return 1.;
    final String lcs = StringHelper.findLongestCommonSubstring(left, right);
    final int leftIndexOfLGS = left.indexOf(lcs);
    final int rightIndexOfLGS = right.indexOf(lcs);
    final int padding = Math.abs(leftIndexOfLGS - rightIndexOfLGS);

    final Map<String, Double> leftMap = mapStringToCharactersDensity(left, padding);
    final Map<String, Double> rightMap = mapStringToCharactersDensity(right, 0);

    final OptionalDouble leftOptResult = computeSimilarity(leftMap, rightMap);
    final OptionalDouble rightOptResult = computeSimilarity(rightMap, leftMap);

    if (leftOptResult.isPresent() && rightOptResult.isPresent()) {
      return (leftOptResult.getAsDouble() + rightOptResult.getAsDouble()) / 2.;
    }

    return .0;
  }

}
