package com.gu.automation

import com.gu.automation.support.WebDriverFeatureSpec

/**
 * Created by ipamer on 19/06/2014.
 */
class LogTest extends WebDriverFeatureSpec {

  feature("My example feature") {

    scenarioWeb("My first test") {

      logger.step("ASDAAAA")

    }

  }

}
