package com.aif.language.sentence.separators.groupers;

import java.util.*;

class PredefinedGrouper implements ISentenceSeparatorsGrouper {

    private static final List<Character> GROUP_1_CHARACTERS = Arrays.asList(new Character[]{
            '.', '!', '?'
    });

    @Override
    public Map<Group, Set<Character>> group(List<String> tokens, List<Character> splitters) {
        final Set<Character> group1 = new HashSet<>();
        final Set<Character> group2 = new HashSet<>();

        splitters.forEach(ch -> {
            if (GROUP_1_CHARACTERS.contains(ch)) {
                group1.add(ch);
            } else {
                group2.add(ch);
            }
        });

        final Map<Group, Set<Character>> groups = new HashMap<>();
        groups.put(Group.GROUP_1, group1);
        groups.put(Group.GROUP_2, group2);
        return groups;
    }

}
