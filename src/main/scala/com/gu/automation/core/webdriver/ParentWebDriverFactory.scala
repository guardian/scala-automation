package com.gu.automation.core

import java.util.concurrent.TimeUnit.SECONDS

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.{CapabilityType, Augmenter, DesiredCapabilities}
import org.openqa.selenium.support.events.EventFiringWebDriver

import com.gu.automation.support.{Config, Browser, TestLogging}
import com.gu.automation.support.page.WaitGet

/**
 * Purpose of this class is to contain common webdriver creation code and delegate to concrete classes for the rest by using the
 * template method pattern
 */
abstract class ParentWebDriverFactory extends TestLogging with WebDriverFactory {

  def createDriver(testCaseName: String, targetBrowser: Browser, capabilities: DesiredCapabilities): WebDriver

  /**
   * Does the common logic for creating a webdriver and delegates to environment specific classes for the specific parts.
   */
  def newInstance(testCaseName: String, targetBrowser: Browser, extraCapabilities: Map[String, String] = Map()): WebDriver = {
    val initialCapabilities = commonCreateCapabilities(targetBrowser, extraCapabilities)
    val initialDriver = createDriver(testCaseName, targetBrowser, initialCapabilities)
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

  private def commonCreateCapabilities(targetBrowser: Browser, extraCapabilities: Map[String, String] = Map()): DesiredCapabilities = {
    val initialCapabilities = targetBrowser.name match {
      case "firefox" => DesiredCapabilities.firefox()
      case "chrome" => DesiredCapabilities.chrome()
      case "ie" => DesiredCapabilities.internetExplorer()
      case default => throw new RuntimeException(s"Browser: [$default] is not supported")
    }
    extraCapabilities.foreach(cap => initialCapabilities.setCapability(cap._1, cap._2))
    if (Config().isAutoAcceptSSLCert()) initialCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)
    initialCapabilities
  }
}
