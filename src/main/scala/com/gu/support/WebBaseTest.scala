package com.gu.support

import org.openqa.selenium.WebDriver

/**
 * Created by jduffell on 10/06/2014.
 */
class WebBaseTest extends BaseTest[WebDriver] {

  protected def startDriver(logger: TestLogger): WebDriver = {
    WebDriverManagement.startWebDriver(logger)
  }

}
