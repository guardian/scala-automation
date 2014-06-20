package com.gu.automation.api

import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by jduffell on 12/06/2014.
 */
class AuthApiTest extends FlatSpec with Matchers {

  "The auth api" should "let us log in as a valid user" in {
    val future = AuthApi.authenticate("johnduffell@guardian.co.uk", "qwerty")

    val accessToken = Await.result(future, 30.seconds)
    println(s"accessToken: $accessToken")
    accessToken should not be empty
  }

}
