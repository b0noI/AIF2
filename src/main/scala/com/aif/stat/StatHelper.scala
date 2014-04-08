package com.aif.stat

import org.apache.commons.math3.stat.descriptive.SummaryStatistics

object StatHelper {

  def variance(values: Array[Double]) = {
    val stat = new SummaryStatistics()
    values.foreach(f => stat.addValue(f))
    scala.math.sqrt(stat.getVariance)
  }

  def variance(values: Array[Int]) = {
    val stat = new SummaryStatistics()
    values.foreach(f => stat.addValue(f))
    scala.math.sqrt(stat.getVariance)
  }

}
