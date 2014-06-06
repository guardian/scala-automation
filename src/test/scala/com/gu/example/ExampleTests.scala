package com.gu.example

import com.gu.support.BaseTest

/**
 * Created by ipamer on 02/06/2014.
 */
class ExampleTests extends BaseTest {

  info("Tests for the Example project")

  feature("My example feature") {

    scenarioWeb("My first test") {

      given {
        ExampleSteps().loggedIn()
      }
      .when {
        _.goToTheEventsPage()
      }
      .then {
        _.seeAListOfEvents()
      }
      .when {
        _.goToTheEventsPage()
      }
      .then {
        _.seeAListOfEvents()
      }

    }

  }

}
