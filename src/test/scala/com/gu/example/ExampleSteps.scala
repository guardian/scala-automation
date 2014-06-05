package com.gu.example

import com.gu.support.BaseSteps

/**
 * Created by ipamer on 02/06/2014.
 */
trait ExampleSteps extends BaseSteps {

  def loggedIn() {
    logger.log("I am logged in")
//    driver.get("http://www.theguardian.com/")
//    new LoginPage(driver).login("it_is_me", "let_me_in")
  }

  def goToTheEventsPage() {
    logger.log("I go to events page")
  }

  def seeAListOfEvents() {
    logger.log("I see a list of events")
  }

}