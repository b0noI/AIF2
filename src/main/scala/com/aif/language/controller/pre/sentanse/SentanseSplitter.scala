package com.aif.language.controller.pre.sentanse

import scala.collection.immutable.HashMap
import scala.annotation.tailrec


class SentanseSplitter {

  def findTechnicalSymbols(tokens: Array[String]): List[Char] = {

    @tailrec
    def parsToken(token: String, index: Int, count: Map[Char, Long], countTail: Map[Char, Long]): (Map[Char, Long], Map[Char, Long]) = {
      if (index == token.length || token.length < 2) (count, countTail)
      else {
        val character = token.charAt(index).toLower
        val halfLength = (token length) / 2
        if (halfLength == 0) return (count, countTail)
        val weight = ((((halfLength - Math.abs(index - halfLength)) / halfLength) + 1) * 100.0).toLong
        parsToken(
          token,
          index + 1,
          count.updated(character, count.getOrElse(character, 0l) + weight),
          if (index == 0 || index == token.length - 1)
            countTail.updated(character, countTail.getOrElse(character, 0l) + 1l)
          else
            countTail
        )
      }
    }

    def merge(left:(Map[Char, Long], Map[Char, Long]), right: (Map[Char, Long], Map[Char, Long])): (Map[Char, Long], Map[Char, Long]) = {
      if (right == null) left
      else if (left == null) right
      else
      (left._1 ++ right._1.map({case (k, v) => k -> (left._1.getOrElse(k, 0l) + right._1.getOrElse(k, 0l))}),
        left._2 ++ right._2.map({case (k, v) => k -> (left._2.getOrElse(k, 0l) + right._2.getOrElse(k, 0l))}))
    }

    @tailrec
    def parsTokens(tokens: Array[String], result: (Map[Char, Long], Map[Char, Long])): (Map[Char, Long], Map[Char, Long]) = {
      if (tokens.isEmpty) result
      else parsTokens(tokens.tail, merge(
        result, parsToken(tokens.head, 0, new HashMap[Char, Long], new HashMap[Char, Long])
      ))
    }

    def merge2(left:Map[Char, List[Char]], right: Map[Char, List[Char]]): Map[Char, List[Char]] = {
      if (right == null) left
      else if (left == null) right
      else
        left ++ right.map({case (k, v) => k -> (left.getOrElse(k, Nil) ::: right.getOrElse(k, Nil))})
    }

    def parsToken2(token: String): Map[Char, List[Char]] = {
      if (token.length < 2)
        null
      else
        new HashMap[Char, List[Char]].updated(token.charAt(token.length - 1), token.charAt(token.length - 2) :: Nil)
    }

    @tailrec
    def parsTokens2(tokens: Array[String], result: Map[Char, List[Char]]): Map[Char, List[Char]] = {
      if (tokens.isEmpty) result
      else parsTokens2(tokens.tail, merge2(
        result, parsToken2(tokens.head)
      ))
    }

    val charactersStat = parsTokens(tokens, (new HashMap[Char, Long], new HashMap[Char, Long]))

    def p(character: Char, charactersStat: (Map[Char, Long], Map[Char, Long])) = {
      if (!charactersStat._2.contains(character))
        0.0
      else {
        val atEnd = charactersStat._2.apply(character).toDouble
        val all = charactersStat._1.apply(character).toDouble
        atEnd / all
      }
    }

    def extractCharacters(x: Seq[(Char, List[Char])], prev: Double): List[Char] = {
      if (x.isEmpty) Nil
      else {
        val midP = x.head._2.map(x => p(x, charactersStat)).sum / x.head._2.length
        val leftP = p(x.head._1, charactersStat)
        val allP = leftP * midP
        if (prev != Double.MinValue && (prev - allP) / prev > 0.3) Nil
        else x.head._1 :: extractCharacters(x.tail, allP)
      }
    }

    extractCharacters(parsTokens2(tokens, new HashMap[Char, List[Char]]).toSeq.sortBy(x => {

      val midP = x._2.map(x => p(x, charactersStat)).sum / x._2.length
      val leftP = p(x._1, charactersStat)
      val allP = leftP * midP
      1.0 - allP

    }), Double.MinValue)
  }

}
