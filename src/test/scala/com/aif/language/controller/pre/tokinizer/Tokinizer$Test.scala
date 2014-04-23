package com.aif.language.controller.pre.tokinizer

import org.scalatest.FunSuite

class Tokinizer$Test extends FunSuite {

  test("find space character: multiply languages") {

    val tokinizer = new TokinizerProbabilityBased

    val languages = Array("arabic", "chinese", "english", "finish", "french", "german",
      "greek", "hindi", "hungirian", "indonesian", "irish", "italian",
      "japanese", "korean", "mandarin", "norwegian", "polish", "portuguese",
      "romanian", "russian", "spanish", "swedish", "thai", "turkish", "vietnamese")

    val fileNameTemplate = "src/test/scala/com/aif/stat/differentLanguages/LawIntro/%s.txt"

    def processLanguage(index: Int): Int = {
      if (index == languages.length)
        0
      else {
        if (tokinizer.getSpace(scala.io.Source.fromFile
          (fileNameTemplate.format(languages(index))).mkString).equals(' '))
          1 + processLanguage(index + 1)
        else
          0 + processLanguage(index + 1)
      }
    }

    val result = processLanguage(0)
    val quality = result.toDouble / languages.length.toDouble
    assert (quality >= 0.8)
  }

}
