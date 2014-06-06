package com.gu.support

import org.scalatest._
import scala.language.experimental.macros

class BaseSteps(val logger: TestLogger) extends Matchers {

  def assert[A](foundValue: A, expectedValue: A, message: String = "") {
    logger.assertion("Expecting '" + foundValue + "' to be '" + expectedValue + "' " + message)
    foundValue should be(expectedValue)
  }

  def assertNotEmpty[A](value: Seq[A], message: String = "") {
    logger.assertion("Expecting '" + value + "' to not be empty " + message)
    value should not be(empty)
  }

}
