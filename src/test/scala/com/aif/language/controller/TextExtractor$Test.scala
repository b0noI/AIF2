package com.aif.language.controller

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TextExtractor$Test extends FunSuite {

  test("parse simple string") {
    val inputString = "test"
    val expected = Map('e' -> new CharacterInfo(Array(1), Array()), 't' -> new CharacterInfo(Array(0,3), Array(3)),
      's'-> new CharacterInfo(Array(2), Array()))

    val actualResult = TextExtractor.parse(inputString)
    assert(actualResult.sameElements(expected))
  }
}
