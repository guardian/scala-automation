package com.gu.automation.core

import org.openqa.selenium.WebDriver
import com.gu.automation.support.Browser

trait WebDriverBase[T <: WebDriver] {

  protected def startDriver(testName: String, targetBrowser: Browser, extraCapabilities: Map[String, String] = Map()): T
  protected def startDriver(testName: String, targetBrowser: Browser): T = startDriver(testName, targetBrowser, Map())
}
