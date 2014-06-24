package com.gu.automation.api

import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by jduffell on 12/06/2014.
 */
class AuthApiTest extends FlatSpec with Matchers {

  val apiRoot = "https://idapi.code.dev-theguardian.com"

  "The auth api" should "let us log in as a valid user" in {
    val future = AuthApi(apiRoot).authenticate("johnduffell@guardian.co.uk", "qwerty")

    val accessToken = Await.result(future, 30.seconds) match {
      case Right(token) => token.toMap
      case Left(error) => fail(error.toString)
    }
    println(s"accessToken: $accessToken")
    accessToken("GU_U") should not be empty
    accessToken("SC_GU_U") should not be empty
  }

  "The auth api" should "return 403 for an invalid user" in {
    val future = AuthApi(apiRoot).authenticate("johnduffell@guardian.co.uk", "qwersty")

    val errorCode = Await.result(future, 30.seconds) match {
      case Right(token) => fail(token.toString)
      case Left(error) => error._1
    }
    println(s"errorCode: $errorCode")
    errorCode should be (403)
  }

}
