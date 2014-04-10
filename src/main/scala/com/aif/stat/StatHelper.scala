package com.aif.stat

import org.apache.commons.math3.stat.descriptive.SummaryStatistics

object StatHelper {

  def variance(values: Array[Int]): Double = {
    if (values.isEmpty) return 0.0
    val stat = new SummaryStatistics()
    val max = values.max
    val min = values.min
    if (max == min) return 0.0
    val normalizedValues = values.map(i => (i - min)/(max - min))
    normalizedValues.foreach(f => stat.addValue(f))
    stat.getVariance
  }

}
