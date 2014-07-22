package com.gu.automation.core

import java.net.URL
import java.util.concurrent.TimeUnit._

import com.gu.automation.support.page.WaitGet
import com.gu.automation.support.{Config, TestLogging}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.{Augmenter, DesiredCapabilities, RemoteWebDriver}
import org.openqa.selenium.support.events.EventFiringWebDriver
import org.openqa.selenium.{JavascriptExecutor, WebDriver}

object WebDriverFactory extends TestLogging {

  val browser: String = Config().getBrowser()
  val webDriverRemoteUrl: String = Config().getWebDriverRemoteUrl()
  val sauceLabsPlatform: Option[String] = Config().getSauceLabsPlatform()
  val browserVersion: Option[String] = Config().getBrowserVersion()

  /**
   * startDriver in the test case base calls this method.
   *
   * If you wish to add capabilities e.g. browserVersion, pass in extraCapabilities to this.
   *
   * If you wish to edit the driver, e.g. change the size, call the methods once the driver is returned to startDriver
   * or the test.
   *
   * If the required changes are common, we can add them to the WebDriverFactory.
   *
   * @param testCaseName passed to saucelabs for test naming
   * @param extraCapabilities any other capabilities you need for your tests
   * @return
   */
  def newInstance(testCaseName: String, extraCapabilities: Map[String,String] = Map()): WebDriver = {
    
    val initialCapabilities = browser match {
      case "firefox" => DesiredCapabilities.firefox()
      case "chrome" => DesiredCapabilities.chrome()
      case "ie" => DesiredCapabilities.internetExplorer()
      case default => throw new RuntimeException(s"Browser: [$default] is not supported")
    }

    val capabilities = augmentCapabilities(testCaseName, initialCapabilities, extraCapabilities)
    val initialDriver = createDriver(capabilities)
    val remoteDriver = augmentDriver(initialDriver)

    val userAgent = remoteDriver.asInstanceOf[JavascriptExecutor].executeScript("return navigator.userAgent")
    logger.info("Started browser: " + userAgent)

    remoteDriver
  }

  private def augmentDriver(driver: WebDriver): WebDriver = {
    val augmentedDriver = new EventFiringWebDriver(new Augmenter().augment(driver))
    augmentedDriver.manage().window().maximize()
    driver.manage().timeouts().implicitlyWait(WaitGet.ImplicitWait, SECONDS)
    augmentedDriver
  }

  private def augmentCapabilities(testCaseName: String, capabilities: DesiredCapabilities, extraCapabilities: Map[String,String]): DesiredCapabilities = {
    capabilities.setCapability("name", testCaseName)
    sauceLabsPlatform.map(capabilities.setCapability("platform", _))
    browserVersion.map(capabilities.setCapability("version", _))
    extraCapabilities.foreach(cap => capabilities.setCapability(cap._1, cap._2))
    capabilities
  }

  private def createDriver(capabilities: DesiredCapabilities): WebDriver = {
    if (webDriverRemoteUrl != "") {
      new RemoteWebDriver(new URL(webDriverRemoteUrl), capabilities)
    } else {
      browser match {
        case "firefox" => new FirefoxDriver(capabilities)
        case "chrome" => new ChromeDriver(capabilities)
        case "ie" => new InternetExplorerDriver(capabilities)
        case default => throw new RuntimeException(s"Browser: [$default] is not supported")
      }
    }
  }

}
