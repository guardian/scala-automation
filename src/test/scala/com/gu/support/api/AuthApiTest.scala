package com.gu.support.api

import org.scalatest._

/**
 * Created by jduffell on 12/06/2014.
 */
class AuthApiTest extends FlatSpec with Matchers {

  "The auth api" should "let us log in as a valid user" in {
    val configLoader = new AuthApi()
    val cookie = configLoader.getCookie("johnduffell@guardian.co.uk", "qwerty")
    cookie should startWith ("{\"status\":\"ok\",\"accessToken\":{\"accessToken\":\"") // TODO parse and validate the json
  }

}
