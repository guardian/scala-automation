package com.gu.automation.core.webdriver

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities
import com.gu.automation.core.ParentWebDriverFactory
import com.gu.automation.support.Config
import com.gu.automation.support.Browser
import org.openqa.selenium.phantomjs.PhantomJSDriver

object LocalWebDriverFactory extends ParentWebDriverFactory {

  override def createDriver(testCaseName: String, targetBrowser: Browser, capabilities: DesiredCapabilities): WebDriver = {
    targetBrowser.name match {
      case "firefox" => new FirefoxDriver(capabilities)
      case "chrome" => new ChromeDriver(capabilities)
      case "ie" => new InternetExplorerDriver(capabilities)
      case "phantomjs" => new PhantomJSDriver(capabilities)
      case default => throw new RuntimeException(s"Browser: [$default] is not supported")
    }
  }
}