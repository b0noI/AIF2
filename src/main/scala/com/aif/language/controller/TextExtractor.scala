package com.aif.language.controller

import scala.collection.mutable.Map
import com.aif.stat.StatHelper
import scala.annotation.tailrec

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

    @tailrec
    def parseData(text: String, toMerge: Map[Char, CharacterInfo]): Map[Char, CharacterInfo] = {
      if(text.length == 1) {
        if (toMerge != null) merge(toMerge, Map(text.head -> new CharacterInfo(Array(data.length - 1), Array())))
        else Map(text.head -> new CharacterInfo(Array(data.length - 1), Array()))
      } else {
        if (toMerge != null) parseData(text.tail, merge(toMerge, Map(text.head -> new CharacterInfo(Array(data.length - text.length), Array()))))
          else
          parseData(text.tail, Map(text.head -> new CharacterInfo(Array(data.length - text.length), Array())))
      }
    }

    def merge(first: Map[Char, CharacterInfo], second: Map[Char, CharacterInfo]): Map[Char, CharacterInfo] = {
      first ++ second.map({case (k, v) => k -> (mergeCharacterInfo(first.getOrElse(k,
        new CharacterInfo(Array(), Array())), second.getOrElse(k,
        new CharacterInfo(Array(), Array()))))})
    }

    parseData(data, null)
  }

}
  object Main {
    def main(args: Array[String]) {
      val first = TextExtractor.parse(scala.io.Source.fromFile("src/test/scala/com/aif/stat/engl1.txt").mkString)
      for (elem <- first.toSeq.sortBy(x => StatHelper.variance(x._2.getDistances()))) {
        println(elem._1, "positions = " ,
          " distances = " + elem._2.getDistances().deep.mkString(","))
        println(" dispersion = " + StatHelper.variance(elem._2.getDistances()))
//        println( elem )
      }
    }
  }
