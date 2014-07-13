package com.gu.automation.support.page

import java.util.concurrent.TimeUnit

import org.openqa.selenium.WebDriver

/**
 * Created by jduffell on 13/07/2014.
 */
object SafeDisplayed {

}

object SafeFindBoolean {
  def apply(get: => Boolean): Boolean = {
    try {
      get
    } catch {
      case e: org.openqa.selenium.NoSuchElementException => false
    }

  }
}

object WaitGet {

  val ImplicitWait = 2

  def apply[A](locator: => A, timeOutInSeconds: Int = 30)(implicit driver: WebDriver): A = {
    driver.manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS)
    val actual = locator
    driver.manage().timeouts().implicitlyWait(ImplicitWait, TimeUnit.SECONDS)
    actual
  }
}

object SafeGet {

  def apply[A](locator: => A): Option[A] = {
    try {
      Some(locator)
    } catch {
      case e: org.openqa.selenium.NoSuchElementException => None
    }
  }
}
