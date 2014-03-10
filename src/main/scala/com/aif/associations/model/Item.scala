package com.aif.associations.model

import javax.validation.constraints.{Max, Min}

trait Item extends Comparable[Item] {

  @Min(value = 0)
  @Max(value = 1)
  def getWeight: Double

  @Min(value = 0)
  @Max(value = 1)
  def getComplexity: Double

  def compareTo(item: Item): Int = {
    return (getWeight - item.getWeight).toInt
  }
}
