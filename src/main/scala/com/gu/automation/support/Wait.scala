package com.gu.automation.support

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

/**
 * Created by ipamer on 17/06/2014.
 */
object Wait {

  def apply()(implicit driver: WebDriver) = new WebDriverWait(driver, 30)

}
