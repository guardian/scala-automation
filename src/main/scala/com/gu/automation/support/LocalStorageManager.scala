package com.gu.automation.support

import org.openqa.selenium.{JavascriptExecutor, WebDriver}

import scala.util.Try

object LocalStorageManager {

  private def executeJavascript(javascript: String)(implicit driver: WebDriver): Try[String] = {
    return Try(driver.asInstanceOf[JavascriptExecutor].executeScript(javascript).toString)
  }

  def remove(key: String)(implicit driver: WebDriver) = {
    executeJavascript(s"localStorage.removeItem('$key');")
  }

  def set(key: String, value: String)(implicit driver: WebDriver) = {
    executeJavascript(s"localStorage.setItem('$key', '$value');")
  }

}