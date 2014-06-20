package com.gu.automation

import com.gu.automation.support.WebBaseTest

/**
 * Created by ipamer on 19/06/2014.
 */
class LogTest extends WebBaseTest {

  feature("My example feature") {

    scenarioWeb("My first test") {

      logger.step("ASDAAAA")

    }

  }

}
