package com.gu.automation.core.webdriver

import java.net.URL
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import com.gu.automation.core.ParentWebDriverFactory
import com.gu.automation.support.Browser
import com.gu.automation.support.Config
import org.apache.commons.lang3.math.NumberUtils._

object SauceLabsWebDriverFactory extends ParentWebDriverFactory {

  val webDriverRemoteUrl: String = Config().getWebDriverRemoteUrl()
  val sauceLabsPlatform: Option[String] = Config().getPlatform()

  override def createDriver(testCaseName: String, targetBrowser: Browser, capabilities: DesiredCapabilities): WebDriver = {
    augmentCapabilities(testCaseName,targetBrowser, capabilities)
    new RemoteWebDriver(new URL(webDriverRemoteUrl), capabilities)
  }

  def augmentCapabilities(testCaseName: String, targetBrowser: Browser, capabilities: DesiredCapabilities): DesiredCapabilities = {
    capabilities.setCapability("name", testCaseName)
    sauceLabsPlatform.foreach(capabilities.setCapability("platform", _))
    Option(targetBrowser.version).filter(isNumber).foreach(capabilities.setCapability("version", _))
    capabilities
  }
}