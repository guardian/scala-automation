package com.gu.automation.core

import org.openqa.selenium.WebDriver

/**
 * Created by ipamer on 04/07/2014.
 */
class WebDriverFeatureSpec extends BaseFeatureSpec[WebDriver] {

    protected def startDriver(testName: String): WebDriver = {
      WebDriverFactory.newInstance(getClass().getSimpleName() + "." + testName)
    }

}
