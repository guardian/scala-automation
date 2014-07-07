package com.gu.example

import com.gu.automation.support.Config
import com.gu.automation.support.page.Element._
import com.gu.automation.support.page.{Element, Wait}
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.{By, WebDriver}

/**
 * Created by ipamer on 02/06/2014.
 */
case class LoginPage(implicit driver: WebDriver) {

  private def pageRoot = Element(By.id("loginArea"))

  private def userTextbox = pageRoot.element(By.id("user"))
  private def passwordTextbox = pageRoot.element(By.id("password"))
  private def submitButton = pageRoot.element(By.cssSelector(".form-field > button"))

  private def userTextboxes2 = pageRoot.elements(By.id("user"))

  def login(user: String, password: String): LoginPage = {
    Wait().until(ExpectedConditions.elementToBeClickable(userTextbox.locator))
    userTextbox.sendKeys(user)
    passwordTextbox.sendKeys(password)
    passwordTextbox.sendKeys(password)
    submitButton.click()

    userTextbox.safeGet.map(_.isDisplayed) match {
      case Some(true) => // do something happy
      case _ => throw new RuntimeException("can't find")
    }

    if (userTextboxes2.filter(_.isDisplayed == true).length > 2) {
      // do something happy
    } else {
      throw new RuntimeException("can't find")
    }

    this
  }

}

object LoginPage {

  def goto()(implicit driver: WebDriver) = {
    driver.get(Config().getTestBaseUrl())
    LoginPage()
  }

}

