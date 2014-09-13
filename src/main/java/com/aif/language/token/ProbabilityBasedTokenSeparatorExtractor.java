package com.aif.language.token;

import com.aif.language.common.VisibilityReducedForTestPurposeOnly;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class ProbabilityBasedTokenSeparatorExtractor implements ITokenSeparatorExtractor {

    @Override
    public Optional<List<Character>> extract(final String txt) {
        final Character[] charactersArray = ArrayUtils.toObject(txt.toCharArray());
        final List<Character> characters = Arrays.asList(charactersArray);

        final Map<Character, Integer> charactersMappedToCount =
                ProbabilityBasedTokenSeparatorExtractor.getCharactersMappedToCount(characters);

        final List<Character> separators =
                ProbabilityBasedTokenSeparatorExtractor.sortBySeparatorProbability(charactersMappedToCount);
        if (separators.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(separators.subList(0, 1));
    }

    @VisibilityReducedForTestPurposeOnly
    static Map<Character, Integer> getCharactersMappedToCount(final List<Character> characters) {
        // we use parallelStream because this list could be HUGE!
        final Map<Character, List<Character>> groupedCharacters = characters.parallelStream()
                .collect(Collectors.groupingBy(Function.identity()));

        return groupedCharacters.entrySet().parallelStream()
                .collect(Collectors.toMap(
                        item -> item.getKey(),
                        item -> item.getValue().size()
                ));
    }

    @VisibilityReducedForTestPurposeOnly
    static List<Character> sortBySeparatorProbability (final Map<Character, Integer> charactersMappedToCount) {
        return charactersMappedToCount.entrySet().parallelStream()
                .map(Map.Entry::getKey)
                .sorted((ch1, ch2) -> charactersMappedToCount.get(ch2).compareTo(charactersMappedToCount.get(ch1)))
                .collect(Collectors.toList());
    }

}
