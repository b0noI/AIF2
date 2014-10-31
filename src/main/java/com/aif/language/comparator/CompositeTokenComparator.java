package com.aif.language.comparator;


import java.util.*;

class CompositeTokenComparator implements ITokenComparator {

    private Collection<Map.Entry<ITokenComparator, Double>> comparatorWeightMap;

    public CompositeTokenComparator(final Collection<Map.Entry<ITokenComparator, Double>> comparatorWeightMap) {
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
