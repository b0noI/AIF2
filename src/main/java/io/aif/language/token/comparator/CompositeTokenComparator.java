package io.aif.language.token.comparator;

import java.util.Map;

class CompositeTokenComparator implements ITokenComparator {

  private Map<ITokenComparator, Double> comparatorWeightMap;

  public CompositeTokenComparator(final Map<ITokenComparator, Double> comparatorWeightMap) {
    this.comparatorWeightMap = comparatorWeightMap;
  }

  @Override
  public Double compare(String t1, String t2) {
    return comparatorWeightMap.entrySet()
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
