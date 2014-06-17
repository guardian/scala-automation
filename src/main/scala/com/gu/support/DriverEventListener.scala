package com.gu.support

import org.openqa.selenium.{By, WebDriver, WebElement}
import org.openqa.selenium.support.events.WebDriverEventListener

/**
 * Created by jao on 23/05/2014.
 */
class DriverEventListener(logger: TestLogger) extends WebDriverEventListener {

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
    logger.driver("Exception found: " + throwable.getClass.getName + ": message: " + throwable.getMessage + " time: " + end)
  }

  def afterNavigateBack(driver: WebDriver) {
    logger.driver("Navigated back: " + end)
  }

  def afterChangeValueOf(element: WebElement, driver: WebDriver) {
    logger.driver("Changed value of " + previousBy + ". Original value: '" + originalValue + "' new value: '" + element.getAttribute("value") + "' time: " + end)
  }

  def afterScript(script: String, driver: WebDriver) {
    logger.driver("Ran script: '" + script + "' time: " + end)
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
    logger.driver("Navigated forward: " + end)
  }

  def afterClickOn(element: WebElement, driver: WebDriver) {
    logger.driver("Clicked on: " + previousBy.toString + " " + end)
  }

  def afterFindBy(by: By, element: WebElement, driver: WebDriver) {
    logger.driver("Found element: " + previousBy.toString + " " + end)
  }

  def beforeNavigateBack(driver: WebDriver) {
    begin
  }

  def beforeScript(script: String, driver: WebDriver) {
    begin
  }

  def afterNavigateTo(url: String, driver: WebDriver) {
    logger.driver("Navigated to: " + url + " " + end)
  }

  def beforeNavigateTo(url: String, driver: WebDriver) {
    begin
  }

}
