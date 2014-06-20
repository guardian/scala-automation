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
    LoginPage()//.login("it_is_me", "let_me_in")
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
