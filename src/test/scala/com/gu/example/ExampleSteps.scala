package com.gu.example

import com.gu.automation.support.{Config, LoggingIn, TestLogging}
import org.openqa.selenium.WebDriver
import org.scalatest.Matchers._

/**
 * Created by ipamer on 02/06/2014.
 */
case class ExampleSteps(implicit driver: WebDriver) extends TestLogging with LoggingIn {

  def loggedIn() = {
    logger.step("I am logged in")
    // need to go to a page before we can inject the cookies
    LoginPage().goto
    /*
    Now we use the API to log in and put the cookies into a file
    your local.conf needs to contain something like:

      user: {
        username : "test.user@guardian.co.uk"
        password : "asdf"
      }

     */
    logIn(Config().getUserValue("username"), Config().getUserValue("password"))
    // need to refresh the page to send the cookies
    LoginPage().goto
    this
  }

  def goToTheEventsPage() = {
    this
  }

  def seeAListOfEvents() = {
    logger.step("I see a list of events")

    "hello" should be("hello")

    this
  }

}
