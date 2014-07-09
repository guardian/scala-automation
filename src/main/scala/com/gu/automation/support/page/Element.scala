package com.gu.automation.support.page

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{WebElement, By, SearchContext, WebDriver}

import scala.collection.JavaConversions._

/**
 * Created by ipamer && jduffell on 04/07/2014.
 */
protected class LazyElement(val locator: By, driver: WebDriver, searchContext: => SearchContext) extends Element {

  override lazy val get = {
    println(s"$locator = $searchContext")
    searchContext.findElement(locator)
  }

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

protected class WrappedElement(webElement: WebElement) extends Element {
  override val get = webElement
  def waitGet(timeOutInSeconds: Long = 30) = get
  def safeGet = Some(get)
}

abstract class Element {
  protected def get: WebElement

  def waitGet(timeOutInSeconds: Long = 30): WebElement
  def safeGet: Option[WebElement]

  def element(locator: By)(implicit driver: WebDriver) = Element.element(locator, driver, get)
  def elements(locator: By) = Elements.elements(locator, get)
}

object Element {
  def apply(locator: By)(implicit driver: WebDriver) = element(locator, driver, driver)

  protected[page] def element(locator: By, driver: WebDriver, searchContext: => SearchContext): Element = new LazyElement(locator, driver, searchContext)

  implicit def elementToWebElement(element: Element): WebElement = element.get
}

object Elements {
  def apply(locator: By)(implicit driver: WebDriver): List[Element] = elements(locator, driver)

  protected[page] def elements(locator: By, searchContext: SearchContext): List[Element] = searchContext.findElements(locator).toList.map(new WrappedElement(_))
}
