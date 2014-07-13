package com.gu.automation.support.page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

/**
 * Created by ipamer on 17/06/2014.
 *
 * Uses explicit wait to wait for something.
 */
trait Wait {

  def explicitWait(implicit driver: WebDriver) = new WebDriverWait(driver, 30)

}
