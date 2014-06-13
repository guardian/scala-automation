package com.gu.example

import com.gu.support.{WebBaseTest, BaseTest}

/**
 * Created by ipamer on 02/06/2014.
 */
class ExampleTests extends WebBaseTest {

  info("Tests for the Example project")

  feature("My example feature") {

    scenarioWeb("My first test", ExampleSteps()) {

      _.given {
        _.loggedIn()
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
