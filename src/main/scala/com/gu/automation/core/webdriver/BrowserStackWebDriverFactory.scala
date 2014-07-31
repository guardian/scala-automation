package com.gu.automation.core.webdriver

import java.net.URL
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import com.gu.automation.core.ParentWebDriverFactory
import com.gu.automation.support.Browser
import com.gu.automation.support.Config
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils._

object BrowserStackWebDriverFactory extends ParentWebDriverFactory {

  val webDriverRemoteUrl: String = Config().getWebDriverRemoteUrl()
  val browserStackOS: Option[String] = Config().getPlatform()
  val browserStackOSVersion: Option[String] = Config().getPlatformVersion()
  val resolution: Option[String] = Config().getResolution()
  val browserStackVisualLog: Option[String] = Config().getBrowserStackVisualLog()

  override def createDriver(testCaseName: String, targetBrowser: Browser, capabilities: DesiredCapabilities): WebDriver = {
    augmentCapabilities(testCaseName, targetBrowser, capabilities)
    new RemoteWebDriver(new URL(webDriverRemoteUrl), capabilities)
  }

  def augmentCapabilities(testCaseName: String, targetBrowser: Browser, capabilities: DesiredCapabilities): DesiredCapabilities = {
    capabilities.setCapability("name", testCaseName)
    browserStackOS.foreach(capabilities.setCapability("os", _))
    browserStackOSVersion.foreach(capabilities.setCapability("os_version", _))
    Option(targetBrowser.version).filter(isNumber).foreach(capabilities.setCapability("browser_version", _))
    resolution.foreach(capabilities.setCapability("resolution", _))
    browserStackVisualLog.foreach(capabilities.setCapability("browserstack.debug", _))
    capabilities
  }
}