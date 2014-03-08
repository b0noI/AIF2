package com.aif.associations.model

import javax.validation.constraints.{Max, Min}

trait AssociationsExtractor[T <: Item, T2 <: Item]{

  @Min(value = 0)
  @Max(value = 1)
  def getAssociationsLevel(connectionExtractor: ConnectionExtractor[T, T2], item1: T, item2: T2): Double
}
