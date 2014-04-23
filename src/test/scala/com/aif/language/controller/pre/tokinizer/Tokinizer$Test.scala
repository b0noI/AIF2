package com.aif.language.controller.pre.tokinizer

import org.scalatest.FunSuite

class Tokinizer$Test extends FunSuite {

  test("find space character: multiply languages") {
    val languages = Array("arabic", "chinese", "english", "finish", "french", "german",
      "greek", "hindi", "hungirian", "indonesian", "irish", "italian",
      "japanese", "korean", "mandarin", "norwegian", "polish", "portuguese",
      "romanian", "russian", "spanish", "swedish", "thai", "turkish", "vietnamese")

    for(language <- languages) {
      val space = Tokinizer.getSpace(scala.io.Source.fromFile
        ("src/test/scala/com/aif/stat/differentLanguages/LawIntro/%s.txt".format(language))
        .mkString)
      println("For %s space is: \"%s\"".format(language, space))
    }
  }

}
