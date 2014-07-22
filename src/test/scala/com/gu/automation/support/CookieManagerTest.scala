package com.gu.automation.support

import com.gu.automation.core.WebDriverFeatureSpec
import org.scalatest.Matchers

class CookieManagerTest extends WebDriverFeatureSpec with Matchers {

  info("Tests for the API Logging in function")

  feature("should be able to add cookies to the browser") {

    scenario("check we can get the right cookie domains") { _ =>

      val cookieManager = new CookieManager

      cookieManager.getCookieDomain("http://www.theguardian.com/uk") should be(".theguardian.com")
      cookieManager.getCookieDomain("https://www.theguardian.com/uk") should be(".theguardian.com")
      cookieManager.getCookieDomain("https://m.code.dev-theguardian.com/") should be(".code.dev-theguardian.com")
    }

  }

}
