package com.gu.support

import org.openqa.selenium.WebDriver

/**
 * Created by jduffell on 10/06/2014.
 */
class WebBaseTest extends BaseTest[WebDriver] {

  override implicit var driver: WebDriver = null

  protected def startDriver(): WebDriver = {
    WebDriverManagement.startWebDriver(logger)
  }

}
