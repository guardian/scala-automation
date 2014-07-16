package com.gu.example

import com.gu.automation.support.TestLogging
import com.gu.example.page.ExamplePage
import org.openqa.selenium.WebDriver
import org.scalatest.Matchers._

/**
 * Created by ipamer on 02/06/2014.
 */
case class ExampleSteps(implicit driver: WebDriver) extends TestLogging {

  def loggedIn() = {
    logger.step("I am logged in")
    /*
     * If you need to be logged in, use
     * https://github.com/guardian/scala-automation-web-signin
     * http://repo1.maven.org/maven2/com/gu/scala-automation-web-signin_2.10/
     * for a quick and reliable method
     */
//    val examplePage = logInToGUPage(ExamplePage.goto)
    ExamplePage.goto()
    this
  }

  def IGoToTheEventsPage() = {
    logger.step("I go to the events page")
    val page = ExamplePage()
      page.waitFor
    page.sendText("cheese")
    page.safeGet
    page.printWhetherPresentAndDisplayed
    page.multiRowList
    val modules = page.getModules
    modules.map(_.getHeading).foreach(h => println(s"heading: $h"))
    this
  }

  def ISeeAListOfEvents() = {
    logger.step("I see a list of events")
    val result = ExamplePage().getFromExample
    result should be("cheese")
    this
  }

}
