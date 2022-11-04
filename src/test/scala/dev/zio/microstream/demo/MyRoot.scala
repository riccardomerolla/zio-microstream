package dev.zio.microstream.demo

import java.util

/**
 * Created by Riccardo Merolla on 04/11/22.
 */
class MyRoot {
  var myObjects = new util.ArrayList[MyData]
}

class MyData(var name: String) {
  private var intValue = 0

  def getName: String = this.name

  def setName(name: String): Unit =
    this.name = name

  def getIntegerValue: Int = this.intValue

  def setIntValue(integerValue: Int): Unit =
    this.intValue = integerValue

  override def toString: String = this.name + " value: " + this.intValue
}
