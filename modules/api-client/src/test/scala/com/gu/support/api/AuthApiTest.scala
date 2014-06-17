package com.gu.support.api

import org.scalatest._

/**
 * Created by jduffell on 12/06/2014.
 */
class AuthApiTest extends FlatSpec with Matchers {

  "The auth api" should "let us log in as a valid user" in {
    val future = AuthApi.getCookie("johnduffell@guardian.co.uk", "qwerty")

    val cookie = "TODO"//Await.result(future, 30.seconds)
    cookie should startWith ("{\"status\":\"ok\",\"accessToken\":{\"accessToken\":\"") // TODO parse and validate the json
  }

}
