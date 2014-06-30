package com.gu.automation.core

import org.openqa.selenium.WebDriver

/**
 * Created by ipamer.
 */
trait WebDriverBase[T <: WebDriver] {

  implicit var driver: T

  protected def startDriver(): T

}
