package com.gu.example

import com.gu.automation.support.{Config, Wait}
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.{By, WebDriver}

/**
 * Created by ipamer on 02/06/2014.
 */
case class LoginPage(implicit driver: WebDriver) {

  private def userTextbox = driver.findElement(By.id("user"))
  private def passwordTextbox = driver.findElement(By.id("password"))
  private def submitButton = driver.findElement(By.cssSelector(".form-field > button"))

  def login(user: String, password: String): LoginPage = {
    Wait().until(ExpectedConditions.elementToBeClickable(userTextbox))
    userTextbox.sendKeys(user)
    passwordTextbox.sendKeys(password)
    submitButton.click()
    this
  }

}

object LoginPage {

  def goto()(implicit driver: WebDriver) = {
    driver.get(Config().getTestBaseUrl())
    LoginPage()
  }

}