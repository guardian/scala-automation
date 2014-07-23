package com.gu.automation.core

import org.openqa.selenium.WebDriver

/**
 * Created by ipamer.
 */
trait WebDriverBase[T <: WebDriver] {

  protected def startDriver(testName: String, extraCapabilities: Map[String, String] = Map()): T
  protected def startDriver(testName: String): T = startDriver(testName, Map())
}
