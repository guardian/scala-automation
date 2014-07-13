package com.gu.example

import com.gu.automation.support.TestLogging
import org.openqa.selenium.WebDriver
import org.scalatest.Matchers._

/**
 * Created by ipamer on 02/06/2014.
 */
case class ExampleSteps(implicit driver: WebDriver) extends TestLogging {

  def loggedIn() = {
    logger.step("I am logged in")
    /*
    We use the API to log in and put the cookies into the browser
    your local.conf needs to contain something like:

      loginEmail : "test.user@guardian.co.uk"
      loginPassword : "asdf"

     */
//    val loginPage = logInToGUPage(LoginPage.goto)
    ExamplePage.goto
    this
  }

  def goToTheEventsPage() = {
    logger.step("I go to the events page")
    ExamplePage().sendToExample("cheese")
    this
  }

  def seeAListOfEvents() = {
    logger.step("I see a list of events")
    val result = ExamplePage().getFromExample
    result should be("cheese")
    this
  }

}
