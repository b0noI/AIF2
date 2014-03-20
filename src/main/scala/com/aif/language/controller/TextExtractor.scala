package com.aif.language.controller

import scala.collection.mutable.Map

object TextExtractor {

  def parse(data: String): Map[Char, Array[Int]] = {

    def parseData(text: String): Map[Char, Array[Int]] = {
      if(text.length == 1) {
        Map(text.head -> Array(data.length - 1))
      } else {
        merge(Map(text.head -> Array(data.length - text.length)), parseData(text.tail))
      }
    }

    def merge(first: Map[Char, Array[Int]], second: Map[Char, Array[Int]]): Map[Char, Array[Int]] = {
      first ++ second.map({case (k, v) => k -> (first.getOrElse(k, Array()) ++ second.getOrElse(k, Array()))})
    }

    parseData(data)
  }

}
  object Main {
    def main(args: Array[String]) {
    val first = TextExtractor.parse("aab")

    for (elem <- first)
      println(elem._1, elem._2.deep.mkString(","))
    }
  }
