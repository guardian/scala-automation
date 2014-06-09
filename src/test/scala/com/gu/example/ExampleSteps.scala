package com.gu.example

import com.gu.support.{TestLogger, BaseSteps}
import org.openqa.selenium.WebDriver

/**
 * Created by ipamer on 02/06/2014.
 */
case class ExampleSteps(implicit val driver: WebDriver, override val logger: TestLogger) extends BaseSteps(logger) {

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
    this
  }

}
