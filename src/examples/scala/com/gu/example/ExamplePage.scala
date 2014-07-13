package com.gu.example

import com.gu.automation.support.Config
import com.gu.automation.support.page.{SafeGet, Wait}
import org.openqa.selenium.By._
import org.openqa.selenium.support.ui.ExpectedConditions._
import org.openqa.selenium.{WebDriver, WebElement}

import scala.collection.JavaConversions._

/**
 * Created by jduffell on 13/07/2014.
 *
 * This is an example of a Page object.  It's a relatively complex example with many alternatives.  Please use the
 * simplest subset required for your project!
 */
case class ExamplePage(implicit driver: WebDriver) {

  /**
   * Having a root is a recommended if your Page/Module/Item represents a section of the actual page
   */
  private def root = driver.findElement(id("root"))


  /**
   * this is an example how to have a default locator, for example if you always locate on a test-id
   *
   * On your project you would probably put this in a trait or object and import it into your page objects.
   */
  implicit def implicitRelativeLocator(testAttribute: String) = xpath(s".//*[@test-id='${testAttribute}']")

  /**
   * This example uses the above implicit locator
   */
  private def buttonUsingImplicitRelative = root findElement "first"


  /**
   * this is an example how to have a default locator from the root, for example if you always locate on a unique test-id
   *
   * On your project you would probably put this in a trait or object and import it into your page objects.
   */
  implicit def implicitAbsoluteLocator(testAttribute: String)(implicit driver: WebDriver) = driver findElement xpath(s"//*[@test-id='${testAttribute}']")

  /**
   * This example uses the above implicit locator
   *
   * Note that if you are caching by using lazy val, or validating the field when the page is created using val, they
   * will only work if you state the type of the val as WebElement.
   */
  private def buttonUsingImplicitAbsolute: WebElement = "sub"


  // this is validated as soon as the page loads
  private val second = root findElement id("second")

  // this is cached after the first time it's used
  private lazy val textbox = root findElement xpath("./*[@name='textbox']")

  private def missing = root findElement "asdasdasd"

  private def rows = root findElements "row"

  def sendToExample(text: String) = {
    Wait.explicitWait.until(elementToBeClickable(buttonUsingImplicitAbsolute))
    textbox.sendKeys(text)
    // click if it's there
    SafeGet.elementOption(missing).map(_.click())
    SafeGet.elementOption(second).map(_.click())

    if (SafeGet.isDisplayed(missing)) {
      println("here - won't happen")
    } else {
      println("not here - as expected")
    }

    println("rows: " + rows.count(_.isDisplayed == true))

    val textPages = rows.map(TextPage(_))
    println("modules: " + textPages)

    textPages
  }

  def getFromExample =
    textbox.getAttribute("value")

}

/**
 * companion object just to let people go to the page.  This is optional, only needed if people need to do that.
 */
object ExamplePage {

  val url = "/example.html"

  def goto()(implicit driver: WebDriver) = {
    driver.get(Config().getTestBaseUrl() + url)
    ExamplePage()
  }

}

/**
 * Should be in another file similar to the above, and root is passed in
 */
case class TextPage(root: WebElement) {
  private def heading = root findElement name("heading")
}

