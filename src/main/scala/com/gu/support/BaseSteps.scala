package com.gu.support

import org.scalatest._
import scala.language.experimental.macros
import org.openqa.selenium.WebDriver
import com.gu.test.TestLogger

trait BaseSteps extends Matchers {

  var driver: WebDriver = null
  var logger: TestLogger = null

  def assert[A](foundValue: A, expectedValue: A, message: String = "") {
    logger.assertion("Expecting '" + foundValue + "' to be '" + expectedValue + "' " + message)
    foundValue should be(expectedValue)
  }

  def assertNotEmpty[A](value: Seq[A], message: String = "") {
    logger.assertion("Expecting '" + value + "' to not be empty " + message)
    value should not be(empty)
  }

}
