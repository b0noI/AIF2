package com.aif.language.word;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class CompositeTokenComparator implements ITokenComparator {

    private List<SimpleEntry<ITokenComparator, Double>> comparatorWeightMap;

    public CompositeTokenComparator(List<SimpleEntry<ITokenComparator, Double>> comparatorWeightMap) {
        this.comparatorWeightMap = comparatorWeightMap;
    }

    @Override
    public Double compare(String t1, String t2) {
        return comparatorWeightMap
                .stream()
                .mapToDouble(comparatorWeight -> {
                    ITokenComparator comparator = comparatorWeight.getKey();
                    Double weight = comparatorWeight.getValue();
                    return comparator.compare(t1, t2) * weight;
                })
                .average()
                .getAsDouble();
    }
}
