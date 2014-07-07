package com.gu.automation.support.page

import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.{By, WebDriver}

import scala.collection.JavaConversions._

/**
 * Created by jduffell on 04/07/2014.
 */
class Element[X](val locator: By, find: By => X, driver: WebDriver) {

  def get = find(locator)

  def waitGet = {
    Wait()(driver).until(ExpectedConditions.presenceOfElementLocated(locator))
    find(locator)
  }

  def safeGet =
    try {
      Some(find(locator))
    } catch {
      case e: NoSuchElementException => None
    }

}

object Element {
  def apply(locator: By)(implicit driver: WebDriver) = new Element(locator, driver.findElement, driver)
}

object Elements {
  def apply(locator: By)(implicit driver: WebDriver) = new Element(locator, driver.findElements(_).toList, driver)
}
