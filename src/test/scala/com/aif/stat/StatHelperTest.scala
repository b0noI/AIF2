package com.aif.stat

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StatHelperTest extends org.scalatest.FunSuite {

    test("simple variance") {
      val inputValues = Array(1, 2, 3, 1, 2, 3)

      val res = StatHelper.variance(inputValues)
      assert(res === 0.8, res)
    }

}