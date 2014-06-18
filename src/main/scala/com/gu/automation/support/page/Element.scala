package com.gu.automation.support.page

import org.openqa.selenium.{By, WebDriver}

/**
 * Created by jduffell on 18/06/2014.
 */
class Element(val by: By) {
  def apply()(implicit driver: WebDriver) = driver.findElement(by)
}

object Element {
  def apply(by: By) = {
    new Element(by)
  }
}
