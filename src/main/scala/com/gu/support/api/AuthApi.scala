package com.gu.support.api

import dispatch._, Defaults._
import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by jduffell on 12/06/2014.
 */
class AuthApi {

  //
  // X-GU-ID-Client-Access-Token: Bearer frontend-code-client-token

  def getCookie(email: String, password: String) = {
    val authUrl = s"https://idapi.code.dev-theguardian.com/auth?email=$email&password=$password"
    val svc = url(authUrl).addHeader("X-GU-ID-Client-Access-Token", "Bearer frontend-code-client-token").POST
    val country = Http(svc OK as.String)
    Await.result(country, 30.seconds)
  }

}
