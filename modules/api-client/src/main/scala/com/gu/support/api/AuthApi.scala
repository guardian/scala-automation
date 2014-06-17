package com.gu.support.api

import dispatch._
import dispatch.liftjson.Js._

/**
 * Created by jduffell on 12/06/2014.
 */
object AuthApi {

  //
  // X-GU-ID-Client-Access-Token: Bearer frontend-code-client-token

  def getCookie(email: String, password: String) = {
    val authUrl = s"https://idapi.code.dev-theguardian.com/auth?email=$email&password=$password"
    val svc = url(authUrl).addHeader("X-GU-ID-Client-Access-Token", "Bearer frontend-code-client-token").POST
//    Http(svc OK as.)

    val http = new Http()
    val u = url("http://gpodder.net/search.json") <<? Map("q" -> "scala")
    http(u ># { json =>
      (json \ "title" children) flatMap( _ match {
        case JField("title", JString(d)) => Some(d)
        case JString(d) => Some(d)
        case _ => None
      })
    })
  }

}
