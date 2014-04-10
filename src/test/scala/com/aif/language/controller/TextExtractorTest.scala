package com.aif.language.controller

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TextExtractorTest extends FunSuite {

  test("merge string information") {
    val firstInfo = new CharacterInfo(Array(1, 3), Array(2))
    val secondInfo = new CharacterInfo(Array(6), Array())
    val expected = new CharacterInfo(Array(1, 3, 6), Array(2, 3))

    val actualResult = TextExtractor.mergeCharacterInfo(firstInfo, secondInfo)
    assert(actualResult.equals(expected))
  }

  test("parse simple string") {
    val inputString = "test"
    val expected = Map('e' -> new CharacterInfo(Array(1), Array()), 't' -> new CharacterInfo(Array(0,3), Array(3)),
      's'-> new CharacterInfo(Array(2), Array()))

    val actualResult = TextExtractor.parse(inputString)
    assert(actualResult.sameElements(expected))
  }
}
