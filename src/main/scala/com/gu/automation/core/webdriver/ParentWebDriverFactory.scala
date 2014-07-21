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

abstract class ParentWebDriverFactory extends TestLogging with WebDriverFactory {

  val browser: String = Config().getBrowser()
  
  def createDriver(testCaseName: String, capabilities: DesiredCapabilities, extraCapabilities: List[(String, String)]): WebDriver

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
  def newInstance(testCaseName: String, extraCapabilities: List[(String, String)] = List()): WebDriver = {
    val initialCapabilities = commonCreateCapabilities
    val initialDriver = createDriver(testCaseName, initialCapabilities, extraCapabilities)
    val augmentedDriver = commonAugmentDriver(initialDriver)

    val userAgent = augmentedDriver.asInstanceOf[JavascriptExecutor].executeScript("return navigator.userAgent")
    logger.info("Started browser: " + userAgent)

    augmentedDriver
  }

  private def commonAugmentDriver(driver: WebDriver): WebDriver = {
    val augmentedDriver = new EventFiringWebDriver(new Augmenter().augment(driver))
    augmentedDriver.manage().window().maximize()
    driver.manage().timeouts().implicitlyWait(WaitGet.ImplicitWait, SECONDS)
    augmentedDriver
  }

  private def commonCreateCapabilities: org.openqa.selenium.remote.DesiredCapabilities = {
    val initialCapabilities = browser match {
      case "firefox" => DesiredCapabilities.firefox()
      case "chrome" => DesiredCapabilities.chrome()
      case "ie" => DesiredCapabilities.internetExplorer()
      case default => throw new RuntimeException(s"Browser: [$default] is not supported")
    }
    initialCapabilities
  }
}
