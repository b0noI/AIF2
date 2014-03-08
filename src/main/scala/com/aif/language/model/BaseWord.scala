package com.aif.language.model

import com.aif.associations.model.Item

trait BaseWord extends Item {

  def getPostfixes: Array[Postfix]

  def getPrefixes: Array[Prefix]

}
