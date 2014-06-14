package com.aif.language.token;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class ProbabilityBasedSeparatorExtractor implements ITokenSeparatorExtractor {

    @Override
    public List<Character> getSeparators(String txt) {
        final Character[] charactersArray = ProbabilityBasedSeparatorExtractor.toCharacterArray(txt.toCharArray());
        final List<Character> characters = Arrays.asList(charactersArray);

        final Map<Character, Integer> charactersMappedToCount =
                ProbabilityBasedSeparatorExtractor.getCharactersMappedToCount(characters);

        final List<Character> separators = ProbabilityBasedSeparatorExtractor.sortBySeparatorProbability(charactersMappedToCount);
        return separators.subList(0, 1);
    }

    private static List<Character> sortBySeparatorProbability (final Map<Character, Integer> charactersMappedToCount) {
        return charactersMappedToCount.entrySet().parallelStream()
                .map(Map.Entry::getKey)
                .sorted((ch1, ch2) -> charactersMappedToCount.get(ch2).compareTo(charactersMappedToCount.get(ch1)))
                .collect(Collectors.toList());
    }

    private static Map<Character, Integer> getCharactersMappedToCount(final List<Character> characters) {
        final Map<Character, List<Character>> groupedCharacters = characters.parallelStream()
                .collect(Collectors.groupingBy(Function.identity()));

        return groupedCharacters.entrySet().parallelStream()
                .collect(Collectors.toMap(
                    item -> item.getKey(),
                    item -> item.getValue().size()
                ));
    }

    private static Character[] toCharacterArray(final char[] originalArray) {
        final Character[] characterArray = new Character[originalArray.length];
        for (int i = 0; i < originalArray.length; i++) {
            characterArray[i] = originalArray[i];
        }
        return characterArray;
    }

}
