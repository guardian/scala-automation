package com.gu.automation.core.webdriver

import java.net.URL
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import com.gu.automation.support.Config
import com.gu.automation.core.ParentWebDriverFactory

object BrowserStackWebDriverFactory extends ParentWebDriverFactory {

  val webDriverRemoteUrl: String = Config().getWebDriverRemoteUrl()
  val sauceLabsPlatform: Option[String] = Config().getSauceLabsPlatform()
  val browserVersion: Option[String] = Config().getBrowserVersion()

  def createDriver(capabilities: DesiredCapabilities): WebDriver = {
    new RemoteWebDriver(new URL(webDriverRemoteUrl), capabilities)
  }

  def augmentDriver(driver: WebDriver): WebDriver = {
    driver
  }

  def augmentCapabilities(testCaseName: String, capabilities: DesiredCapabilities, extraCapabilities: List[(String, String)]): DesiredCapabilities = {
    capabilities.setCapability("name", testCaseName)
    sauceLabsPlatform.foreach(capabilities.setCapability("platform", _))
    browserVersion.foreach(capabilities.setCapability("version", _))
    extraCapabilities.foreach(cap => capabilities.setCapability(cap._1, cap._2))
    capabilities
  }
}