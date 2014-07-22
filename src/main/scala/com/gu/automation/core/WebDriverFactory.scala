package com.gu.automation.core

import org.openqa.selenium.WebDriver

import com.gu.automation.core.webdriver.BrowserStackWebDriverFactory
import com.gu.automation.core.webdriver.LocalWebDriverFactory
import com.gu.automation.core.webdriver.SauceLabsWebDriverFactory
import com.gu.automation.support.Config

trait WebDriverFactory {

  /**
   * startDriver in the test case base calls this method.
   *
   * If you wish to add capabilities e.g. browserVersion, pass in extraCapabilities to this.
   *
   * If you wish to edit the driver, e.g. change the size, call the methods once the driver is returned to startDriver
   * or the test.
   *
   * If the required changes are common, we can add them to the WebDriverFactory.
   *
   * @param testCaseName passed to saucelabs for test naming
   * @param extraCapabilities any other capabilities you need for your tests
   * @return
   */
  def newInstance(testCaseName: String, extraCapabilities: Map[String,String] = Map()): WebDriver
}

object WebDriverFactory extends WebDriverFactory {
  
  val environment: String = Config().getBrowserEnvironment
  
  def newInstance(testCaseName: String, extraCapabilities: Map[String,String] = Map()): WebDriver = {
    environment match {
      case "local" => LocalWebDriverFactory.newInstance(testCaseName, extraCapabilities)
      case "sauceLabs" => SauceLabsWebDriverFactory.newInstance(testCaseName, extraCapabilities)
      case "browserStack" => BrowserStackWebDriverFactory.newInstance(testCaseName, extraCapabilities)
      case default => LocalWebDriverFactory.newInstance(testCaseName, extraCapabilities)
    } 
  }
}

