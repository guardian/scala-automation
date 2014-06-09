package com.gu.support

import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory

abstract class BasePage(createdDriver: WebDriver) {

  val driverWait = new WebDriverWait(createdDriver, 30)
  val driver = createdDriver

  PageFactory.initElements(driver, this)

//  abstract def at[T >: BasePage](): T

}
