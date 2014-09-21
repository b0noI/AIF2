package com.aif.language.sentence.separators.groupers;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISentenceSeparatorsGrouper {

    public Map<Group, Set<Character>> group(final List<String> tokens, final List<Character> splitters);

    public enum Group {

        GROUP_1,
        GROUP_2

    }

    public enum Type {

        PREDEFINED(new PredefinedGrouper()),
        PROBABILITY(new StatGrouper());

        private final ISentenceSeparatorsGrouper instance;

        Type(ISentenceSeparatorsGrouper instance) {
            this.instance = instance;
        }

        public ISentenceSeparatorsGrouper getInstance() {
            return instance;
        }

    }

}
