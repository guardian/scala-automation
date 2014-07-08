package com.gu.automation.support.page

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{WebElement, By, SearchContext, WebDriver}

import scala.collection.JavaConversions._

/**
 * Created by ipamer && jduffell on 04/07/2014.
 */
class Element(val locator: By, driver: WebDriver, searchContext: => SearchContext) {

  lazy val get = searchContext.findElement(locator)

  def waitGet(timeOutInSeconds: Long = 30) = {
    new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.presenceOfElementLocated(locator))
    get
  }

  def safeGet =
    try {
      Some(get)
    } catch {
      case e: NoSuchElementException => None
    }

}

class FindContext(searchContext: => SearchContext) {
  def element(locator: By)(implicit driver: WebDriver) = new Element(locator, driver, searchContext)
  def elements(locator: By) = searchContext.findElements(locator).toList
}

object Element {
  def apply(locator: By)(implicit driver: WebDriver) = new FindContext(driver).element(locator)

  implicit def webElement(element: Element): WebElement = element.get
  implicit def augmentWebElement(webElement: => WebElement) = new FindContext(webElement)
  implicit def augmentElement(element: Element) = augmentWebElement(webElement(element))
}

object Elements {
  def apply(locator: By)(implicit driver: WebDriver): List[WebElement] = new FindContext(driver).elements(locator)
}
