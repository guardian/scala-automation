package com.gu.automation.core

import java.net.URL

import com.gu.automation.support.{Config, TestLogging}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.{Augmenter, DesiredCapabilities, RemoteWebDriver}
import org.openqa.selenium.support.events.EventFiringWebDriver
import org.openqa.selenium.{JavascriptExecutor, WebDriver}

trait WebDriverFactory extends TestLogging {

  val browser: String = Config().getBrowser()
  val webDriverRemoteUrl: String = Config().getWebDriverRemoteUrl()

  /**
   * This method is called after the driver has been created. Override this to augment it with special driver initialization
   */
  def augmentDriver(driver: WebDriver): WebDriver

  /**
   * This method is called after the Capabilities but before the webdriver has been created. Override this to augment it
   * with special driver initialization
   */
  def augmentCapabilities(testCaseName: String, capabilities: DesiredCapabilities, extraCapabilities: List[(String, String)]): DesiredCapabilities


  def newInstance(testCaseName: String, extraCapabilities: List[(String,String)] = List()): WebDriver = {
    
    val (driver, initialCapabilities): (DesiredCapabilities => WebDriver, DesiredCapabilities) = browser match {
      case "firefox" => (new FirefoxDriver(_), DesiredCapabilities.firefox())
      case "chrome" => (new ChromeDriver(_), DesiredCapabilities.chrome())
      case "ie" => (new InternetExplorerDriver(_), DesiredCapabilities.internetExplorer())
      case default => throw new RuntimeException(s"Browser: [$default] is not supported")
    }

    val capabilities = augmentCapabilities(testCaseName, initialCapabilities, extraCapabilities)
    val initialDriver = createDriver(capabilities, driver)
    val remoteDriver = augmentDriver(initialDriver)
    val userAgent = remoteDriver.asInstanceOf[JavascriptExecutor].executeScript("return navigator.userAgent")
    logger.info("Started browser: " + userAgent)
    remoteDriver
  }

  private def createDriver(capabilities: DesiredCapabilities, driver: (DesiredCapabilities) => WebDriver): WebDriver = {
    if (webDriverRemoteUrl != "") {
      new RemoteWebDriver(new URL(webDriverRemoteUrl), capabilities)
    } else {
      driver(capabilities)
    }
  }
}

object WebDriverFactory extends WebDriverFactory {

  def augmentDriver(driver: WebDriver): WebDriver = {
    val augmentedDriver = new EventFiringWebDriver(new Augmenter().augment(driver))
    augmentedDriver.manage().window().maximize()
    augmentedDriver
  }

  def augmentCapabilities(testCaseName: String, capabilities: DesiredCapabilities, extraCapabilities: List[(String, String)]): DesiredCapabilities = {
    Config().getCapabilities().map(_.foreach(cap => capabilities.setCapability(cap._1, cap._2)))
    extraCapabilities.foreach(cap => capabilities.setCapability(cap._1, cap._2))
    capabilities.setCapability("name", testCaseName)
    return capabilities
  }
}
