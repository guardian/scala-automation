package com.gu.automation.support.page

import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.{WebElement, By, WebDriver}

import scala.collection.JavaConversions._

/**
 * Created by jduffell on 04/07/2014.
 */
class Element[X](val locator: By, find: Element[X] => X, driver: WebDriver) {

  def waitGet = {
    Wait()(driver).until(ExpectedConditions.presenceOfElementLocated(locator))
    find(this)
  }

  def safeGet =
    try {
      Some(find(this))
    } catch {
      case e: NoSuchElementException => None
    }

}

object Element {
  def apply(locator: By)(implicit driver: WebDriver) = new Element(locator, webElement, driver)
  implicit def webElement(value: Element[WebElement])(implicit driver: WebDriver) = driver.findElement(value.locator)
}

object Elements {
  def apply(locator: By)(implicit driver: WebDriver) = new Element(locator, webElements, driver)
  implicit def webElements(value: Element[List[WebElement]])(implicit driver: WebDriver) = driver.findElements(value.locator).toList
}
