package com.aif.language.controller

class CharacterInfo(positions: Array[Double], distances: Array[Double]) {
  def getPositions() = positions

  def getDistances() = distances

  override def equals(obj:Any) = {

    (obj.isInstanceOf[CharacterInfo] && obj.asInstanceOf[CharacterInfo].getPositions().sameElements(this
      .getPositions()) && obj.asInstanceOf[CharacterInfo].getDistances().sameElements(this.getDistances()))
  }
}
