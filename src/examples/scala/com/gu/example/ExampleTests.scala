package com.gu.example

import com.gu.automation.core.{GivenWhenThen, WebDriverFeatureSpec}
import org.openqa.selenium.WebDriver

/**
 * Created by ipamer on 02/06/2014.
 *
 * To run this test against example.html, just add a local.conf with something like the following line:
 *
 * "testBaseUrl"           : "file:///Users/jduffell/ws/scala-automation/src/examples/resources"
 */
class ExampleTests extends WebDriverFeatureSpec with GivenWhenThen {

  info("Tests for the Example project")

  feature("My example feature") {

    scenarioWeb("My first test") { implicit driver: WebDriver =>

      given {
        ExampleSteps().loggedIn()
      }
      .when {
        _.goToTheEventsPage()
      }
      .then {
        _.seeAListOfEvents()
      }
//      .when {
//        _.goToTheEventsPage()
//      }
//      .then {
//        _.seeAListOfEvents()
//      }

    }

  }

}
