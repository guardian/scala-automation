package com.gu.support

import org.scalatest.Matchers

/**
 * Created by ipamer on 17/06/2014.
 */
object Assert extends Matchers {

  def assert[A](foundValue: A, expectedValue: A, message: String = "")(implicit logger: TestLogger) {
    logger.assertion("Expecting '" + foundValue + "' to be '" + expectedValue + "' " + message)
    foundValue should be(expectedValue)
  }

  def assertNotEmpty[A](value: Seq[A], message: String = "")(implicit logger: TestLogger) {
    logger.assertion("Expecting '" + value + "' to not be empty " + message)
    value should not be(empty)
  }

}
