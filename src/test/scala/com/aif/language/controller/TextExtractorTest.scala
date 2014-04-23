package com.aif.language.controller

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.junit.Ignore

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

  test("find space character") {

    val expectedCharacter = ' '

    val secondText = TextExtractor.parse(scala.io.Source.fromFile("src/test/scala/com/aif/stat/engl2.txt").mkString)

    val thirdText = TextExtractor.parse(scala.io.Source.fromFile("src/test/scala/com/aif/stat/engl3.txt").mkString)

    expectResult(expectedCharacter) {TextExtractor.getSpace(secondText)}
    expectResult(expectedCharacter) {TextExtractor.getSpace(thirdText)}
  }

  test("find space character: multiply languages") {
    val languages = Array("arabic", "chinese", "english", "finish", "french", "german",
      "greek", "hindi", "hungirian", "indonesian", "irish", "italian",
      "japanese", "korean", "mandarin", "norwegian", "polish", "portuguese",
      "romanian", "russian", "spanish", "swedish", "thai", "turkish", "vietnamese")

    for(language <- languages) {
      val textInfo = TextExtractor.parse(scala.io.Source.fromFile
        ("src/test/scala/com/aif/stat/differentLanguages/LawIntro/%s.txt".format(language))
        .mkString)
      println("For %s space is: \"".format(language) + TextExtractor.getSpace(textInfo) + "\"")
    }
  }
}
