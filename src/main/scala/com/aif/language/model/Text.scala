package com.aif.language.model

import com.aif.associations.model.ConnectionExtractor

trait Text {

  def getBaseWordConnectionExtractor: ConnectionExtractor[BaseWord, BaseWord]

  def getPostfixToPrefixConnectionExtractor: ConnectionExtractor[Postfix, Prefix]

}
