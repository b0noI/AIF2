package com.aif.language.sentence.separators.groupers;

import java.util.*;

class PredefinedGrouper implements ISeparatorsGrouper {

    private static final List<Character> GROUP_1_CHARACTERS = Arrays.asList(new Character[]{
            '.', '!', '?'
    });

    @Override
    public List<Set<Character>> group(List<String> tokens, List<Character> splitters) {
        final Set<Character> group1 = new HashSet<>();
        final Set<Character> group2 = new HashSet<>();

        splitters.forEach(ch -> {
            if (GROUP_1_CHARACTERS.contains(ch)) {
                group1.add(ch);
            } else {
                group2.add(ch);
            }
        });

        final List<Set<Character>> groups = new ArrayList<>(2);
        groups.add(group1);
        groups.add(group2);
        return groups;
    }

}
