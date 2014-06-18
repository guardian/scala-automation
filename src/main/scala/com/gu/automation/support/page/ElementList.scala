package com.gu.automation.support.page

import org.openqa.selenium.{By, WebDriver}

/**
 * Created by jduffell on 18/06/2014.
 */
class ElementList(val by: By) {
  def apply()(implicit driver: WebDriver) = driver.findElements(by)
}

object ElementList {
  def apply(by: By) = {
    new ElementList(by)
  }
}
