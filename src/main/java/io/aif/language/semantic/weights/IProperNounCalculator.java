package io.aif.language.semantic.weights;

import io.aif.language.word.IWord;

/**
 * Created by ifthen on 3/9/15.
 */
public interface IProperNounCalculator {

    public static enum Type {

        TITLECASE_PROPER_NOUN_CALCULATOR(new TitleCaseProperNounCalculator());

        private final IProperNounCalculator instance;

        private Type(final IProperNounCalculator instance) {
            this.instance = instance;
        }

        public IProperNounCalculator getInstance() {
            return instance;
        }

    }

    public static IProperNounCalculator getDefault() {
        return Type.TITLECASE_PROPER_NOUN_CALCULATOR.getInstance();
    }

    double calculate(IWord word);

}
