package com.gu.automation.support.page

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{WebElement, By, SearchContext, WebDriver}

import scala.collection.JavaConversions._

/**
 * Created by ipamer && jduffell on 04/07/2014.
 */
abstract class Element {
  protected def get: SearchContext
  def >>(locator: By) = new LazyElement(locator, get)
}

protected class LazyElement(val locator: By, val searchContext: => SearchContext) extends Element {

  override lazy val get = searchContext.findElement(locator)

  def waitGet(timeOutInSeconds: Long = 30)(implicit driver: WebDriver) = {
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

object Element {
  
  implicit def elementToWebElement(element: LazyElement): WebElement = element.get

  implicit def lazyElementToElements(locator: LazyElement) = locator.searchContext.findElements(locator.locator).toList

  implicit def searchContextAugmentWithArrows(searchContext: SearchContext) = new Element {
    override protected def get: SearchContext = searchContext
  }
  
}
