package com.gu.automation.core

import com.gu.automation.support.TestLogger

/**
 * Created by ipamer on 30/06/2014.
 */
trait WhenOrThen {

  def given[A](body: => A)(implicit logger: TestLogger) = {
    logger.setPhase("GIVEN")
    new WhenOrThen(body)
  }

  class WhenOrThen[A](val input: A) {

    def when[B](body: A => B)(implicit logger: TestLogger): WhenOrThen[B] = {
      logger.setPhase("WHEN")
      new WhenOrThen(body(input))
    }

    def then[B](body: A => B)(implicit logger: TestLogger): WhenOrThen[B] = {
      logger.setPhase("THEN")
      new WhenOrThen(body(input))
    }

  }

}
