package com.aif.r

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class RWrapperTest extends org.scalatest.FunSuite {

  test("simple eval") {
    val res = RWrapper.evalR("1 + 1")
    assert(res === "2.0", res)
  }

}
