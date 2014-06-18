package com.gu.automation.support

import java.util.{ArrayList, List}

class TestLogger(val name: String) {

  private val messages: List[String] = new ArrayList[String]
  private var phase: String = "GIVEN"

  def dumpMessages() {
    System.out.println(name)
    import scala.collection.JavaConversions._
    for (s <- messages) {
      System.out.println(s)
    }
  }

  def setPhase(phase: String) {
    this.phase = phase
  }

  def log(str: String) {
    addMessage(phase + " " + str)
    setPhase("AND")
  }

  def assertion(msg: String) {
    addMessage("Assertion :" + msg)
  }

  def failure(str: String) {
    addMessage("Fail: " + str)
  }

  def driver(msg: String) {
    addMessage("Driver: " + msg)
  }

  def addMessage(message: String) {
    messages.add(System.currentTimeMillis + ": " + message)
  }

}
