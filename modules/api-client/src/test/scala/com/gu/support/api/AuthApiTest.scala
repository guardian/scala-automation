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

    val accessToken = Await.result(future, 30.seconds).toMap
    println(s"accessToken: $accessToken")
    accessToken("GU_U") should not be empty
    accessToken("SC_GU_U") should not be empty
  }

}
