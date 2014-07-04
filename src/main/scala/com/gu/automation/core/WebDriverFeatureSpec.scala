package com.gu.automation.core

import org.openqa.selenium.WebDriver

/**
 * Created by ipamer on 04/07/2014.
 */
class WebDriverFeatureSpec extends BaseFeatureSpec[WebDriver] {

    override implicit var driver: WebDriver = null

    protected def startDriver(): WebDriver = {
      WebDriverManagement.startWebDriver(testName)
    }

}
