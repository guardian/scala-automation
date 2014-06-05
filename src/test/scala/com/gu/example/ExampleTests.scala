package com.gu.example

import com.gu.support.BaseTest
import com.typesafe.config.ConfigFactory

/**
 * Created by ipamer on 02/06/2014.
 */
class ExampleTests extends BaseTest with ExampleSteps {

  info("Tests for the Example project")

  feature("My example feature") {

    scenarioWeb("My first test") {

      given
      loggedIn

      when
      goToTheEventsPage
      
      then
      seeAListOfEvents

    }

  }

}
