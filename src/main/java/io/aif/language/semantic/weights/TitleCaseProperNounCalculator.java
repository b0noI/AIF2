package io.aif.language.semantic.weights;

import io.aif.language.common.IFuzzyBoolean;
import io.aif.language.common.StringHelper;
import io.aif.language.word.IWord;

import java.util.Set;

public class TitleCaseProperNounCalculator implements IProperNounCalculator {

    @Override
    public double calculate(IWord word) {
        Set<String> tokens = word.getAllTokens();

        // WARN! This is to handle the zero length case and avoid division by zero at the bottom
        if (tokens.size() == 0)
            return 0d;

        long numTokensUpperCase  =  tokens
                .stream()
                .filter(StringHelper::startsWithUpperCase)
                .count();
        return (double)numTokensUpperCase / tokens.size();
    }
}
