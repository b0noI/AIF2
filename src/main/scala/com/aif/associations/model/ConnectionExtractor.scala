package com.aif.associations.model

import javax.validation.constraints.{Max, Min}


trait ConnectionExtractor[T <: Item, T2 <: Item]{

  @Min(value = 0)
  @Max(value = 1)
  def getProbabilityOfHavingItemsInOneExperiment(item1: T, item2: T2): Double

  @Min(value = 0)
  @Max(value = 1)
  def getApproximateIntervalBetweenItems(item1: T, item2: T2): Double

}
