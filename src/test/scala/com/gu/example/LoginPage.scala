package com.gu.example

import com.gu.automation.support.page.{Wait, ElementList, Element}
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.{By, WebDriver}

/**
 * Created by ipamer on 02/06/2014.
 */
case class LoginPage(implicit driver: WebDriver) {

  private def userTextbox = Element(By.id("user"))
  private def userTextboxes = ElementList(By.id("users"))
  private def passwordTextbox = Element(By.id("password"))
  private def submitButton = Element(By.cssSelector(".form-field>button"))

  def login(user: String, password: String): LoginPage = {
    Wait().until(ExpectedConditions.presenceOfElementLocated(userTextbox.by))
    userTextbox().sendKeys(user)
    userTextboxes().iterator()
    passwordTextbox().sendKeys(password)
    submitButton().click()
    this
  }

}
