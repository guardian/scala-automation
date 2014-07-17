package com.gu.automation.support.page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

/**
 *
 * Uses explicit wait to wait for something.
 */
trait Wait {

  def explicitWait(implicit driver: WebDriver) = new WebDriverWait(driver, 30)

}
