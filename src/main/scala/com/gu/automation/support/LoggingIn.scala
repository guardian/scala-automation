package com.gu.automation.support

import com.gu.automation.api.AuthApi
import org.openqa.selenium.{Cookie, WebDriver}
import scala.concurrent.duration._

import scala.concurrent.Await

/**
 * Created by jduffell on 20/06/2014.
 */
trait LoggingIn {

  def addGULoginCookies(email: String = Config().getLoginEmail(), password: String = Config().getLoginPassword())(implicit driver: WebDriver) = {
    val future = AuthApi.authenticate(email, password)

    val accessToken = Await.result(future, 30.seconds)
    val cookies = accessToken match { case Right(cookies) => cookies }
    cookies.foreach {
      case (key, value) =>
        val isSecure = key.startsWith("SC_")
        val cookie = new Cookie(key, value, null, "/", null, isSecure, isSecure)
        driver.manage().addCookie(cookie)
    }
  }

  def logInToGUPage[P](goto: () => P)(implicit driver: WebDriver): P = {
    goto()
    addGULoginCookies()
    goto()
  }

}
