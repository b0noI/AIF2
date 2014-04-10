package com.aif.language.controller

import scala.collection.mutable.{ArrayBuffer, Map}
import com.aif.stat.StatHelper
import scala.collection.mutable.Map
import com.aif.stat.StatHelper
import scala.annotation.tailrec

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

  def getSpace(characters: Map[Char, CharacterInfo]): Char = {
    characters.toSeq.sortBy(x => {
      val variance = StatHelper.variance(x._2.getDistances())
      if (variance == 0.0) 1
      else StatHelper.variance(x._2.getDistances())}).head._1
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
      val first = TextExtractor.parse("Poor Androcles was in despair; he had not strength to rise and run away, " +
      "and there was the lion coming upon him. But when the great beast came up to him instead of attacking him it " +
      "kept on moaning and groaning and looking at Androcles, who saw that the lion was holding out his right paw, which was covered with blood and much swollen. Looking more closely at it Androcles saw a great big thorn pressed into the paw, which was the cause of all the lion’s trouble. Plucking up courage he seized hold of the thorn and drew it out of the lion’s paw, who roared with pain when the thorn came out, but soon after found such relief from it that he rubbed up against Androcles and showed, in every way that he knew, that he was truly thankful for being relieved from such pain.")

      first.toSeq.sortBy(x => {
        val variance = StatHelper.variance(x._2.getDistances())
        if (variance == 0.0) 1
        else StatHelper.variance(x._2.getDistances())}).foreach(x => println(x._1))

      println("Space is: \"" + TextExtractor.getSpace(first) + "\"")

      main2()
    }
    def main2() {
      val first = TextExtractor.parse(scala.io.Source.fromFile("src/test/scala/com/aif/stat/engl1.txt").mkString)
      println("Space is: \"" + TextExtractor.getSpace(first) + "\"")
      val first2 = TextExtractor.parse(scala.io.Source.fromFile("src/test/scala/com/aif/stat/engl2.txt").mkString)
      println("Space 2 is: \"" + TextExtractor.getSpace(first2) + "\"")
    }
  }
