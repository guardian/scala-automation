package com.gu.support

import org.scalatest._
import scala.language.experimental.macros
import org.openqa.selenium.WebDriver
import com.gu.test.TestLogger

trait BaseSteps extends GivenWhenThen with Informing with Matchers {

  var driver: WebDriver = null
  var logger: TestLogger = null

  def given(logger: TestLogger, message: String) {
    Given(message)
    logger.given(message)
  }

  def when(logger: TestLogger, message: String) {
    When(message)
    logger.when(message)
  }

  def then(logger: TestLogger, message: String) {
    Then(message)
    logger.then(message)
  }

  def verify[A](logger: TestLogger, foundValue: A, expectedValue: A, message: String = "") {
    logger.assertion("Expecting '" + foundValue + "' to be '" + expectedValue + "' " + message)
    foundValue should be(expectedValue)
  }

  def verifyNotEmpty[A](logger: TestLogger, value: Seq[A], message: String = "") {
    logger.assertion("Expecting '" + value + "' to not be empty " + message)
    value should not be(empty)
  }

}
