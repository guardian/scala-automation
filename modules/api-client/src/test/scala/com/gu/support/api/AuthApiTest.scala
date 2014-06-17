package com.gu.support.api

import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration._

import scala.concurrent.Await

/**
 * Created by jduffell on 12/06/2014.
 */
class AuthApiTest extends FlatSpec with Matchers {

  "The auth api" should "let us log in as a valid user" in {
    val future = AuthApi.getCookie("johnduffell@guardian.co.uk", "qwerty")

    val cookie = Await.result(future, 30.seconds)
    cookie should startWith ("{\"status\":\"ok\",\"accessToken\":{\"accessToken\":\"") // TODO parse and validate the json
  }

}
