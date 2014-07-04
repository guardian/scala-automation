package com.gu.automation.core

import java.net.URL

import com.gu.automation.support.{Config, TestLogging}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.{Augmenter, DesiredCapabilities, RemoteWebDriver}
import org.openqa.selenium.support.events.EventFiringWebDriver

object WebDriverManagement extends TestLogging {

  val browser: String = Config().getBrowser()
  val webDriverRemoteUrl: String = Config().getWebDriverRemoteUrl()

  def startWebDriver(testName: String): WebDriver = {
    if (webDriverRemoteUrl == "") {
      getLocalWebDriver()
    } else {
      val caps = getCapabilities()
      caps.setCapability("name", testName) // saucelabs title
      val remoteDriver = new EventFiringWebDriver(new Augmenter().augment(new RemoteWebDriver(new URL(webDriverRemoteUrl), caps)))
          .register(new DriverEventListener())
      val userAgent = remoteDriver.executeScript("return navigator.userAgent")
      logger.info("Started browser: " + userAgent)
      remoteDriver
    }
  }

  private def getLocalWebDriver(): WebDriver = browser match {
    case "firefox" => new FirefoxDriver()
    case "chrome" => new ChromeDriver()
    case "ie" => new InternetExplorerDriver()
  }
  private def getCapabilities(): DesiredCapabilities = browser match {
    case "chrome" => DesiredCapabilities.chrome()
    case "firefox" => DesiredCapabilities.firefox()
    case "ie" => DesiredCapabilities.internetExplorer()
  }
}
