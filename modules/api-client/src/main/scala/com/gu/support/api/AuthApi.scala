package com.gu.automation.api

import dispatch._
import dispatch.liftjson.Js._
import com.stackmob.newman._
import com.stackmob.newman.dsl._
import scala.concurrent._
import scala.concurrent.duration._
import java.net.URL

/**
 * Created by jduffell on 12/06/2014.
 */
object AuthApi {

  //
  // X-GU-ID-Client-Access-Token: Bearer frontend-code-client-token

  def getCookie(email: String, password: String) = {
    val authUrl = s"https://idapi.code.dev-theguardian.com/auth?email=$email&password=$password"
    implicit val httpClient = new ApacheHttpClient
    //execute a GET request
    val url = new URL("http://google.com")
    val rawFuture = GET(url).addHeaders(("X-GU-ID-Client-Access-Token", "Bearer frontend-code-client-token")).apply

    // todo turn raw future into JSON and get the cookie out to return
  }

}
