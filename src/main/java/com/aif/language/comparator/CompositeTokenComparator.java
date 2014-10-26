package com.aif.language.comparator;


import java.util.*;

class CompositeTokenComparator implements ITokenComparator {

    private List<Map.Entry<ITokenComparator, Double>> comparatorWeightMap;

    // TODO: This is ugly and there is no point in constructing an empty comparator
    public CompositeTokenComparator() { }

    public CompositeTokenComparator(List<Map.Entry<ITokenComparator, Double>> comparatorWeightMap) {
        this.comparatorWeightMap = comparatorWeightMap;
    }

    // TODO: Not very friendly design as the requirement to set comparators in implicit
    public void setComparators(List<Map.Entry<ITokenComparator, Double>> comparatorWeightMap) {
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
