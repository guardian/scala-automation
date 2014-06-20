package com.gu.automation.api

import com.ning.http.client.AsyncHttpClientConfig
import play.api.libs.json.JsArray
import play.api.libs.ws._
import play.api.libs.ws.ning._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by jduffell on 12/06/2014.
 */
object AuthApi {

  def authenticate(email: String, password: String) = {
    val authUrl = s"https://idapi.code.dev-theguardian.com/auth?email=$email&password=$password&format=cookies"
    val config = new AsyncHttpClientConfig.Builder().build()
    val client: WSClient = new NingWSClient(config)
    val response = client.url(authUrl).withHeaders("X-GU-ID-Client-Access-Token" -> "Bearer frontend-code-client-token").post("")
    response.map{ resp =>
      (resp.json \ "cookies" \ "values").as[JsArray].value.map { cookie =>
        ((cookie \ "key").as[String], (cookie \ "value").as[String])
      }
    }
  }

}
