package com.gu.automation.support.page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

/**
 *
 * Uses explicit wait to wait for something.
 *
 * for example: ExplicitWait().until(elementToBeClickable(myWebElement))
 */
object ExplicitWait {

  def apply(timeout: Int = 30)(implicit driver: WebDriver) = new WebDriverWait(driver, timeout)

}
