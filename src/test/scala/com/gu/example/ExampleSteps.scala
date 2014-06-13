package com.gu.example

import com.gu.support.{TestLogger, BaseSteps}
import org.openqa.selenium.WebDriver

/**
 * Created by ipamer on 02/06/2014.
 */
class ExampleSteps(val page: LoginPage, override val logger: TestLogger) extends BaseSteps(logger) {

  def loggedIn() = {
    logger.log("I am logged in")
    //driver.get("http://www.theguardian.com/")
    page//.login("it_is_me", "let_me_in")
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

object ExampleSteps {
  def apply()(driver: WebDriver, logger: TestLogger) =
      new ExampleSteps(LoginPage(driver), logger)
}