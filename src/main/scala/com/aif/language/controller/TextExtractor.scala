package com.aif.language.controller

import scala.collection.mutable.Map

object TextExtractor {

  def mergeCharacterInfo(first : CharacterInfo, second : CharacterInfo) : CharacterInfo = {

    def calculateDistances(first : CharacterInfo, second : CharacterInfo) : Array[Int] = {
      val distances = first.getDistances() ++ second.getDistances()
      if (first.getPositions().length > 0 && second.getPositions().length > 0) {
        distances :+ second.getPositions()(0) - first.getPositions()(first.getPositions().length - 1)
      } else distances
    }
    new CharacterInfo(first.getPositions() ++ second.getPositions(), calculateDistances(first, second))
  }

  def parse(data: String): Map[Char, CharacterInfo] = {

    def parseData(text: String): Map[Char, CharacterInfo] = {
      if(text.length == 1) {
        Map(text.head -> new CharacterInfo(Array(data.length - 1), Array()))
      } else {
        merge(Map(text.head -> new CharacterInfo(Array(data.length - text.length), Array())), parseData(text.tail))
      }
    }

    def merge(first: Map[Char, CharacterInfo], second: Map[Char, CharacterInfo]): Map[Char, CharacterInfo] = {
      first ++ second.map({case (k, v) => k -> (mergeCharacterInfo(first.getOrElse(k,
        new CharacterInfo(Array(), Array())), second.getOrElse(k,
        new CharacterInfo(Array(), Array()))))})
    }

    parseData(data)
  }

}
  object Main {
    def main(args: Array[String]) {
    val first = TextExtractor.parse("aababbac")

    for (elem <- first)
      println(elem._1, "positions = " + elem._2.getPositions().deep.mkString(","),
        " distances = " + elem._2.getDistances().deep.mkString(","))
    }
  }
