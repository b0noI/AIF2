package io.aif.language.fact;


import io.aif.common.FuzzyBoolean;
import io.aif.language.semantic.weights.IProperNounCalculator;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.stream.Stream;

public class FactHelper {

    private final static IProperNounCalculator PROPER_NOUN_CALCULATOR = IProperNounCalculator.getDefault();

    public static Stream<IWord> getProperNouns(final Stream<IWord> words) {
        return words.filter(word ->
            new FuzzyBoolean(PROPER_NOUN_CALCULATOR.calculate(word)).isTrue()
        );
    }

}
