package com.gu.automation.core

import java.net.URL
import java.util.concurrent.TimeUnit._
import com.gu.automation.support.page.WaitGet
import com.gu.automation.support.{ Config, TestLogging }
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.{ Augmenter, DesiredCapabilities, RemoteWebDriver }
import org.openqa.selenium.support.events.EventFiringWebDriver
import org.openqa.selenium.{ JavascriptExecutor, WebDriver }
import com.gu.automation.core.webdriver.LocalWebDriverFactory
import com.gu.automation.core.webdriver.SauceLabsWebDriverFactory

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
  def newInstance(testCaseName: String, extraCapabilities: List[(String, String)] = List()): WebDriver
}

object WebDriverFactory extends WebDriverFactory {
  
  val environment: String = Config().getEnvironment
  
  def newInstance(testCaseName: String, extraCapabilities: List[(String, String)] = List()): WebDriver = {
    environment match {
      case "local" => LocalWebDriverFactory.newInstance(testCaseName, extraCapabilities)
      case "sauceLabs" => SauceLabsWebDriverFactory.newInstance(testCaseName, extraCapabilities)
      case "browserStack" => SauceLabsWebDriverFactory.newInstance(testCaseName, extraCapabilities)
      case default => LocalWebDriverFactory.newInstance(testCaseName, extraCapabilities)
    } 
  }
}

