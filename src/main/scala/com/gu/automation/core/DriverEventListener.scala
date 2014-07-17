package com.gu.automation.core

import com.gu.automation.support.TestLogging
import org.openqa.selenium.support.events.WebDriverEventListener
import org.openqa.selenium.{By, WebDriver, WebElement}

/**
 */
class DriverEventListener() extends WebDriverEventListener with TestLogging {

  private var commandStart: Long = 0L
  private var originalValue: String = null
  private var previousBy: By = null

  private def end: Long = {
    return System.currentTimeMillis - commandStart
  }

  private def begin {
    commandStart = System.currentTimeMillis
  }

  def beforeClickOn(element: WebElement, driver: WebDriver) {
    begin
  }

  def onException(throwable: Throwable, driver: WebDriver) {
    logDriver("Exception found: " + throwable.getClass.getName + ": message: " + throwable.getMessage + " time: " + end)
  }

  def afterNavigateBack(driver: WebDriver) {
    logDriver("Navigated back: " + end)
  }

  def afterChangeValueOf(element: WebElement, driver: WebDriver) {
    logDriver("Changed value of " + previousBy + ". Original value: '" + originalValue + "' new value: '" + element.getAttribute("value") + "' time: " + end)
  }

  def afterScript(script: String, driver: WebDriver) {
    logDriver("Ran script: '" + script + "' time: " + end)
  }

  def beforeFindBy(by: By, element: WebElement, driver: WebDriver) {
    begin
    previousBy = by
  }

  def beforeNavigateForward(driver: WebDriver) {
    begin
  }

  def beforeChangeValueOf(element: WebElement, driver: WebDriver) {
    begin
    originalValue = element.getAttribute("value")
  }

  def afterNavigateForward(driver: WebDriver) {
    logDriver("Navigated forward: " + end)
  }

  def afterClickOn(element: WebElement, driver: WebDriver) {
    logDriver("Clicked on: " + previousBy.toString + " " + end)
  }

  def afterFindBy(by: By, element: WebElement, driver: WebDriver) {
    logDriver("Found element: " + previousBy.toString + " " + end)
  }

  def beforeNavigateBack(driver: WebDriver) {
    begin
  }

  def beforeScript(script: String, driver: WebDriver) {
    begin
  }

  def afterNavigateTo(url: String, driver: WebDriver) {
    logDriver("Navigated to: " + url + " " + end)
  }

  def beforeNavigateTo(url: String, driver: WebDriver) {
    begin
  }

  private def logDriver(msg: String) {
    logger.info("DRIVER: " + msg)
  }

}
