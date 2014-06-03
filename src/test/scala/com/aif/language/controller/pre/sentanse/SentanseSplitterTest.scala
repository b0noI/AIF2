package com.aif.language.controller.pre.sentanse

import org.scalatest.FunSuite
import com.aif.language.controller.pre.tokinizer.{TokinizerCharacterBased, TokinizerProbabilityBased}

class SentanseSplitterTest extends FunSuite {

  test("find space character: multiply languages") {

    val tokinizer = new TokinizerCharacterBased
    val sentSplitter = new SentanseSplitter

    val languages = Array("arabic", "chinese", "english", "finish", "french", "german",
      "greek", "hindi", "hungirian", "indonesian", "irish", "italian",
      "japanese", "korean", "mandarin", "norwegian", "polish", "portuguese",
      "romanian", "russian", "spanish", "swedish", "thai", "turkish", "vietnamese")

    val fileNameTemplate = "src/test/scala/com/aif/stat/differentLanguages/LawIntro/%s.txt"

    def processLanguage(lang: String) = {
      val tokens = tokinizer.tokinize(
        scala.io.Source.fromFile
          (fileNameTemplate.format(lang)).mkString
      )
      println("test %s : %s".format(lang, sentSplitter.findTechnicalSymbols(tokens)))
    }

    languages.foreach(lang => processLanguage(lang))

  }

}
