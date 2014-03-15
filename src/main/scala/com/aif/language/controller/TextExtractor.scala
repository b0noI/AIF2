package com.aif.language.controller

import scala.io.Source
import scala.collection.mutable.Map

class TextExtractor {
  def parseFile(fileToOpen: String): Map[Char, Array[Int]] = {
    val distanceMap: Map[Char, Array[Int]] = Map()
    val source = Source.fromFile(fileToOpen)
    try {
      var position = 0
      for (elem <- source.toArray) {
        if (distanceMap.contains(elem)) {
          distanceMap(elem) :+= position
        } else {
          distanceMap += elem -> Array(position)
        }
        position += 1
      }
    } finally {
      source.close()
    }
    return distanceMap
  }

}
  object Main {
    def main(args: Array[String]) {
      val textExtractor: TextExtractor = new TextExtractor
      val map = textExtractor.parseFile("src/main/resources/text#1")
      for (elem <- map)
        println(elem._1, elem._2.deep.mkString(","))
    }
  }
