package com.gu.automation.support.page

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, SearchContext, WebDriver}

import scala.collection.JavaConversions._

/**
 * Created by jduffell on 04/07/2014.
 */
class Element(val locator: By, driver: WebDriver, searchContext: => SearchContext) {

  def get = searchContext.findElement(locator)

  def waitGet = {
    new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(locator))
    get
  }

  def safeGet =
    try {
      Some(get)
    } catch {
      case e: NoSuchElementException => None
    }

  // provide a way of getting an element within another element
  def element(locator: By) = new Element(locator, driver, get)

  def elements(locator: By) = get.findElements(locator).toList
}

object Element {
  def apply(locator: By)(implicit driver: WebDriver) = new Element(locator, driver, driver)
  // provide all WebElement methods on our Element class
  implicit def webElement(value: Element) = value.get

}

object Elements {
  def apply(locator: By)(implicit driver: WebDriver) = driver.findElements(locator).toList
}
