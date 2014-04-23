package com.aif.language.controller.pre.tokinizer

import scala.collection.mutable.Map
import scala.annotation.tailrec

trait Tokinizer {

  def tokinize(data: String): Array[String] = {
    data.split(getSpace(data))
  }

  def getSpace(data: String): Char

}

class TokinizerCharacterBased extends Tokinizer {

  def getSpace(data: String): Char = {
    ' '
  }

}

class TokinizerProbabilityBased extends Tokinizer {

  def getSpace(data: String): Char = {
    getSpace(parse(data))
  }

  private def getSpace(characters: Map[Char, CharacterInfo]): Char = {

    val valuesList = characters.valuesIterator.toList
    val sum = valuesList.map(_.getPositions().length).sum

    characters.toSeq.sortBy(x => {
      1.0 - x._2.getPositions().length.toDouble / sum.toDouble
    }).head._1
  }

  private def mergeCharacterInfo(first : CharacterInfo, second : CharacterInfo) : CharacterInfo = {

    def calculateDistances(first : CharacterInfo, second : CharacterInfo) : Array[Double] = {
      val distances = first.getDistances() ++ second.getDistances()
      if (first.getPositions().length > 0 && second.getPositions().length > 0) {
        distances :+ second.getPositions()(0) - first.getPositions()(first.getPositions().length - 1)
      } else distances
    }
    new CharacterInfo(first.getPositions() ++ second.getPositions(), calculateDistances(first, second))
  }

  private def parse(data: String): Map[Char, CharacterInfo] = {

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
