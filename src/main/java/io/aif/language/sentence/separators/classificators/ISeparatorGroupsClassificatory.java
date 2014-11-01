package io.aif.language.sentence.separators.classificators;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISeparatorGroupsClassificatory {

    public Map<Group, Set<Character>> classify(final List<String> tokens, final List<Set<Character>> separatorsGroups);

    public enum Group {

        GROUP_1,
        GROUP_2

    }

    public enum Type {

        PREDEFINED(new PredefinedSeparatorGroupsClassificatory()),
        PROBABILITY(new StatSeparatorGroupsClassificatory());

        private final ISeparatorGroupsClassificatory instance;

        Type(ISeparatorGroupsClassificatory instance) {
            this.instance = instance;
        }

        public ISeparatorGroupsClassificatory getInstance() {
            return instance;
        }

    }

}
