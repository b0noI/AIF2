package io.aif.language.ner.noun;

import io.aif.fuzzy.IFuzzySet;
import io.aif.language.word.IWord;

@FunctionalInterface
public interface IProperNounSet extends IFuzzySet<IWord> {

    public static IProperNounSet getDefault() {
        return Type.TITLECASE_PROPER_NOUN_CALCULATOR.getInstance();
    }

    public static enum Type {

        TITLECASE_PROPER_NOUN_CALCULATOR(new TitleCaseProperNounSet());

        private final IProperNounSet instance;

        private Type(final IProperNounSet instance) {
            this.instance = instance;
        }

        public IProperNounSet getInstance() {
            return instance;
        }

    }

}
