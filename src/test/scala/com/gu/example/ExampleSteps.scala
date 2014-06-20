package com.gu.example

import com.gu.automation.support.TestLogging
import org.openqa.selenium.WebDriver

/**
 * Created by ipamer on 02/06/2014.
 */
case class ExampleSteps(implicit driver: WebDriver) extends TestLogging {

  def loggedIn() = {
    logger.step("I am logged in")
    logger.info("I am logged in")
    //driver.get("http://www.theguardian.com/")
    LoginPage()//.login("it_is_me", "let_me_in")
    this
  }

  def goToTheEventsPage() = {
//    logger.log("I go to events page")
    this
  }

  def seeAListOfEvents() = {
//    logger.log("I see a list of events")
    this
  }

}
