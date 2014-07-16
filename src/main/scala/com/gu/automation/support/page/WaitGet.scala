package com.gu.automation.support.page

import java.util.concurrent.TimeUnit

import org.openqa.selenium.WebDriver

/**
 * Created by jduffell on 13/07/2014.
 *
 * Waits for the presence of an element for other than the default time, useful if something is likely to be slow to
 * appear, or if something should be there already and you just want to check the presence.
 */
trait WaitGet {

  def waitGet[A](locator: => A, timeOutInSeconds: Int = 30)(implicit driver: WebDriver): A = {
    driver.manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS)
    val actual = locator
    driver.manage().timeouts().implicitlyWait(WaitGet.ImplicitWait, TimeUnit.SECONDS)
    actual
  }
}
object WaitGet {

  val ImplicitWait = 2

}