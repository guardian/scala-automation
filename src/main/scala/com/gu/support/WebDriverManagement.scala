package com.gu.support

import java.util.concurrent.TimeUnit
import org.openqa.selenium.remote.{Augmenter, RemoteWebDriver, DesiredCapabilities}
import java.net.URL
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.ie.InternetExplorerDriver

object WebDriverManagement {

  val browser: String = Config().getBrowser()
  val webDriverRemoteUrl: String = Config().getWebDriverRemoteUrl()

  def startWebDriver(logger: TestLogger): WebDriver = {
    if (webDriverRemoteUrl == "") {
      return getLocalWebDriver()
    } else {
      val caps = getCapabilities()
      val driver = new EventFiringWebDriver(new Augmenter().augment(new RemoteWebDriver(new URL(webDriverRemoteUrl), caps)))
          .register(new DriverEventListener(logger))
      driver.manage.timeouts.implicitlyWait(30, TimeUnit.SECONDS)
      val userAgent = driver.executeScript("return navigator.userAgent")
      logger.addMessage("Started browser: " + userAgent)
      return driver
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
