package com.aif.associations.model

import javax.validation.constraints.{Max, Min}

trait Item {

  @Min(value = 0)
  @Max(value = 1)
  def getWeight: Double

  @Min(value = 0)
  @Max(value = 1)
  def getComplexety: Double

  def compareTo(item: IItem): Int = {
    if (item.getWeight == getWeight) return 0
    if (item.getWeight > getWeight) return -1
    return 1
  }

}
