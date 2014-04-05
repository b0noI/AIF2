package com.aif.language.controller

import scala.collection.mutable.{ArrayBuffer, Map}
import com.aif.stat.StatHelper

object TextExtractor {

  def mergeCharacterInfo(first : CharacterInfo, second : CharacterInfo) : CharacterInfo = {

    def calculateDistances(first : CharacterInfo, second : CharacterInfo) : Array[Double] = {
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
      val first = TextExtractor.parse("Poor Androcles was in despair; he had not strength to rise and run away, " +
      "and there was the lion coming upon him. But when the great beast came up to him instead of attacking him it " +
      "kept on moaning and groaning and looking at Androcles, who saw that the lion was holding out his right paw, which was covered with blood and much swollen. Looking more closely at it Androcles saw a great big thorn pressed into the paw, which was the cause of all the lion’s trouble. Plucking up courage he seized hold of the thorn and drew it out of the lion’s paw, who roared with pain when the thorn came out, but soon after found such relief from it that he rubbed up against Androcles and showed, in every way that he knew, that he was truly thankful for being relieved from such pain.")

      val variances = ArrayBuffer[Double]()
      for (elem <- first) {
        val variance = StatHelper.variance(elem._2.getPositions)
        variances += variance
        println("Element: " + elem._1, "Elements number: " + elem._2.getPositions().length,
          " Dispersion: " + variance)
      }
      println(variances.sorted.mkString(","))
    }
  }
