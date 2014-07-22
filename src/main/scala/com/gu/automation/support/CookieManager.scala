package com.gu.automation.support

import org.openqa.selenium.{Cookie, WebDriver}

class CookieManager {

  def getCookieDomain(url: String) =
    """http(s?)://([^.]*(\.))?([^/]+).*$""".r.replaceAllIn(url, "$3$4")

  def addCookies(driver: WebDriver, cookies: Seq[(String, String)]) {
    val baseUrl = Config().getTestBaseUrl()
    val loginDomain = getCookieDomain(baseUrl)

    driver.get(baseUrl) // have to be on the right url to add the cookies

    cookies.foreach {

      case (key, value) =>
        val isSecure = key.startsWith("SC_")
        val cookie = new Cookie(key, value, loginDomain, "/", null, isSecure, isSecure)
        driver.manage().addCookie(cookie)
    }
  }

}
