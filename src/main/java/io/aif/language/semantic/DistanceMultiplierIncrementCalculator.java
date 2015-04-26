package io.aif.language.semantic;

import io.aif.associations.model.IDistanceMultiplierIncrementCalculator;
import io.aif.language.common.StringHelper;
import io.aif.language.sentence.separators.classificators.ISeparatorGroupsClassifier;
import io.aif.language.word.IWord;

import java.util.Map;
import java.util.Set;


class DistanceMultiplierIncrementCalculator implements IDistanceMultiplierIncrementCalculator<IWord> {

    private final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separators;

    DistanceMultiplierIncrementCalculator(final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separators) {
        this.separators = separators;
    }

    @Override
    public double calculateMultiplierIncrement(final IWord vertex) {
        Character charToken = StringHelper
                .castToChar(vertex.getRootToken())
                .orElse(null);
        return getMultiplierValue(charToken);
    }

    private int getMultiplierValue(Character charToken) {
        int result = 0;
        // TODO: Can we have the same seperator in both the groups.
        // TODO: contains does not have for string
        if (separators.get(ISeparatorGroupsClassifier.Group.GROUP_2).contains(charToken)) {
            result = 1;
        }
        if (separators.get(ISeparatorGroupsClassifier.Group.GROUP_1).contains(charToken)) {
            result = 4;
        }
        return result;
    }

}
