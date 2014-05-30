package com.gu.support

import java.util.concurrent.TimeUnit
import org.openqa.selenium.remote.{Augmenter, RemoteWebDriver, DesiredCapabilities}
import java.net.URL
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver
import com.gu.test.{DriverEventListener, TestLogger}
import org.openqa.selenium.firefox.FirefoxDriver

trait WebDriverManagement {

  val browser: String
  val hub: String

  def startWebDriver(logger: TestLogger): WebDriver = {
    val caps = startBrowser()
//    val driver =
//      new EventFiringWebDriver(new Augmenter().augment(new RemoteWebDriver(new URL(hub), caps)))
//        .register(new DriverEventListener(logger))
//    driver.manage.timeouts.implicitlyWait(30, TimeUnit.SECONDS)
//    val userAgent = driver.executeScript("return navigator.userAgent")
//    logger.addMessage("Started browser: " + userAgent)
//    driver
    new FirefoxDriver()
  }

  private def startBrowser(): DesiredCapabilities = browser match {
      case "chrome" => DesiredCapabilities.chrome()
      case "firefox" => DesiredCapabilities.firefox()
      case "ie" => DesiredCapabilities.internetExplorer()
  }
}

object WebDriverManagement extends WebDriverManagement {
  override val browser = "chrome" // TODO get config here
  override val hub = "http://localhost:4444/wd/hub" // TODO add url from config here
}