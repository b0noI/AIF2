package com.aif.language.sentence.separators.clasificators;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISentenceSeparatorGroupsClassificatory {

    public Map<Group, Set<Character>> classify(final List<String> tokens, final List<Set<Character>> separatorsGroups);

    public enum Group {

        GROUP_1,
        GROUP_2

    }

    public enum Type {

        PREDEFINED(new PredefinedSentenceSeparatorGroupsClassificatory()),
        PROBABILITY(new StatSentenceSeparatorGroupsClassificatory());

        private final ISentenceSeparatorGroupsClassificatory instance;

        Type(ISentenceSeparatorGroupsClassificatory instance) {
            this.instance = instance;
        }

        public ISentenceSeparatorGroupsClassificatory getInstance() {
            return instance;
        }

    }

}
