package com.gu.automation.core

import com.gu.automation.support.TestLogger
import org.slf4j.MDC

/**
 * Created by ipamer on 30/06/2014.
 */
trait GivenWhenThen {

  def given[A](body: => A)(implicit logger: TestLogger) = {
    MDC.put("phase", "GIVEN")
    new WhenOrThen(body)
  }

  class WhenOrThen[A](val input: A) {

    def when[B](body: A => B)(implicit logger: TestLogger): WhenOrThen[B] = {
      MDC.put("phase", "WHEN")
      new WhenOrThen(body(input))
    }

    def then[B](body: A => B)(implicit logger: TestLogger): WhenOrThen[B] = {
      MDC.put("phase", "THEN")
      new WhenOrThen(body(input))
    }

  }

}
