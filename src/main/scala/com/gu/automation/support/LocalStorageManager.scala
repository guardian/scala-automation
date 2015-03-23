package com.gu.automation.support

import org.openqa.selenium.{JavascriptExecutor, WebDriver}

import scala.util.Try

object LocalStorageManager {

  private def stripQuotes(input: String): String = {
    return input filterNot ("'\"" contains _)
  }

  private def executeJavascript(javascript: String)(implicit driver: WebDriver): Try[String] = {
    return Try(driver.asInstanceOf[JavascriptExecutor].executeScript(javascript).toString)
  }

  def remove(key: String)(implicit driver: WebDriver) = {
    executeJavascript("localStorage.removeItem(\"%s\");" format (stripQuotes(key)))
  }

  def set(key: String, value: String)(implicit driver: WebDriver) = {
    executeJavascript("localStorage.setItem(\"%s\", \"%s\");" format (stripQuotes(key), stripQuotes(value)))
  }

}