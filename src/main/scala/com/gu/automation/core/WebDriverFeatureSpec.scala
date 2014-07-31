package com.gu.automation.core

import org.openqa.selenium.WebDriver
import com.gu.automation.support.Browser

class WebDriverFeatureSpec extends BaseFeatureSpec[WebDriver] {

  /**
   * Override this method in your test to control driver creation for the whole testclass.
   *
   * To add project specific capabilities just pass in the optional extra capabilities to WebDriverFactory.newInstance
   *
   * To change the browser settings, just call driver.manage()..... on the returned driver from WebDriverFactory.newInstance
   *
   * @param testName
   * @return
   */
  override protected def startDriver(testName: String, targetBrowser: Browser, extraCapabilities: Map[String, String] = Map()): WebDriver = {
    WebDriverFactory.newInstance(getClass().getSimpleName() + "." + testName, targetBrowser, extraCapabilities)
  }

}
