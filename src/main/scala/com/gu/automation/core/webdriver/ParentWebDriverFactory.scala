package com.gu.automation.core

import java.util.concurrent.TimeUnit.SECONDS

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.Augmenter
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.events.EventFiringWebDriver

import com.gu.automation.support.Config
import com.gu.automation.support.TestLogging
import com.gu.automation.support.page.WaitGet

/**
 * Purpose of this class is to contain common webdriver creation code and delegate to concrete classes for the rest by using the
 * template method pattern
 */
abstract class ParentWebDriverFactory extends TestLogging with WebDriverFactory {

  val browser: String = Config().getBrowser()

  def createDriver(testCaseName: String, capabilities: DesiredCapabilities, extraCapabilities: List[(String, String)]): WebDriver

  /**
   * Does the common logic for creating a webdriver and delegates to environment specific classes for the specific parts.
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
