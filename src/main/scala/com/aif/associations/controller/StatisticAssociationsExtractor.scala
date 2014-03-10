package com.aif.associations.controller

import com.aif.associations.model.{Item, ConnectionExtractor, AssociationsExtractor}
import javax.validation.constraints.{Max, Min}

class StatisticAssociationsExtractor[T <: Item, T2 <: Item] extends AssociationsExtractor[T, T2] {

  private val NUMERATOR_MULTIPLIER = 2.0

  @Min(value = 0)
  @Max(value = 1)
  def getAssociationsLevel(connectionExtractor: ConnectionExtractor[T, T2], item1: T, item2: T2): Double = {
    val numerator = calculateNumerator(connectionExtractor, item1, item2)
    val denominator = calculateDenominator(connectionExtractor, item1, item2)
    return numerator / denominator
  }

  private def calculateNumerator(connectionExtractor: ConnectionExtractor[T, T2], item1: T, item2: T2): Double = {
    val probabilityOfHavingItemsInOneExperiment = connectionExtractor.getProbabilityOfHavingItemsInOneExperiment(item1, item2)
    val aproxWeight = (item1.getWeight + item2.getWeight) / 2.0
    return NUMERATOR_MULTIPLIER * aproxWeight * Math.exp(probabilityOfHavingItemsInOneExperiment)
  }

  private def calculateDenominator(connectionExtractor: ConnectionExtractor[T, T2], item1: T, item2: T2): Double = {
    val itemsDifficult = (item1.getComplexity + item2.getComplexity) / 2.0
    val aproxInterval = connectionExtractor.getApproximateIntervalBetweenItems(item1, item2)
    return itemsDifficult * aproxInterval
  }

}
