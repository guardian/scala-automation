package com.gu.example.page

import org.openqa.selenium.By._
import org.openqa.selenium.WebDriver

/**
 * Created by jduffell on 13/07/2014.
 *
 * This example is for projects where you have a standard way of locating items, e.g. test-id and want the syntax to
 * be really simple so that people are encouraged to do it the standard way.
 */
object TestIdLocator {

  /**
   * this is an example how to have a default locator, for example if you always locate on a test-id
   *
   * On your project you would probably put this in a trait or object and import it into your page objects.
   */
  implicit def implicitRelativeLocator(testAttribute: String) = xpath(s".//*[@test-id='${testAttribute}']")

  /**
   * this is an example how to have a default locator from the root, for example if you always locate on a unique test-id
   *
   * On your project you would probably put this in a trait or object and import it into your page objects.
   */
  implicit def implicitAbsoluteLocator(testAttribute: String)(implicit driver: WebDriver) = driver findElement xpath(s"//*[@test-id='${testAttribute}']")

}
