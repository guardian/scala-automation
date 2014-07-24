package com.gu.automation.core.webdriver

import java.net.URL
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import com.gu.automation.support.Config
import com.gu.automation.core.ParentWebDriverFactory

object SauceLabsWebDriverFactory extends ParentWebDriverFactory {

  val webDriverRemoteUrl: String = Config().getWebDriverRemoteUrl()
  val sauceLabsPlatform: Option[String] = Config().getPlatform()
  val browserVersion: Option[String] = Config().getBrowserVersion()

  override def createDriver(testCaseName: String, capabilities: DesiredCapabilities): WebDriver = {
    augmentCapabilities(testCaseName, capabilities)
    new RemoteWebDriver(new URL(webDriverRemoteUrl), capabilities)
  }

  def augmentCapabilities(testCaseName: String, capabilities: DesiredCapabilities): DesiredCapabilities = {
    capabilities.setCapability("name", testCaseName)
    sauceLabsPlatform.foreach(capabilities.setCapability("platform", _))
    browserVersion.foreach(capabilities.setCapability("version", _))
    capabilities
  }
}