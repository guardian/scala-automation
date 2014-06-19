package com.gu.example

import com.gu.automation.support.{LogMatchers, TestLogger}
import org.openqa.selenium.WebDriver

/**
 * Created by ipamer on 02/06/2014.
 */
case class ExampleSteps(implicit driver: WebDriver, logger: TestLogger) extends LogMatchers {

  def loggedIn() = {
    logger.log("I am logged in")
    //driver.get("http://www.theguardian.com/")
    LoginPage()//.login("it_is_me", "let_me_in")
    this
  }

  def goToTheEventsPage() = {
    logger.log("I go to events page")
    this
  }

  def seeAListOfEvents() = {
    logger.log("I see a list of events")

    "hello" lshould be("hello")

    this
  }

}
