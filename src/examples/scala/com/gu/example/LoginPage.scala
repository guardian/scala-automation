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

  private lazy val pageRoot = Element(By.xpath("/*"))

  private def userTextbox = pageRoot.element(By.xpath(".//*"))
  private def passwordTextbox = pageRoot.element(By.xpath("//*"))
  private def submitButton = pageRoot.element(By.cssSelector(".form-field > button"))

  private def textboxes = pageRoot.elements(By.id("user"))

  def login(user: String, password: String): LoginPage = {
    Wait().until(ExpectedConditions.elementToBeClickable(userTextbox))
    userTextbox.sendKeys(user)
    passwordTextbox.sendKeys(password)
    passwordTextbox.sendKeys(password)
    submitButton.click()

    userTextbox.safeGet.map(_.isDisplayed) match {
      case Some(true) => // do something happy
      case _ => throw new RuntimeException("can't find")
    }

    if (textboxes.filter(_.isDisplayed == true).length > 2) {
      // do something happy
    } else {
      throw new RuntimeException("can't find")
    }

    this
  }

  def test() = {
    if (userTextbox.safeGet != None && passwordTextbox.safeGet != None) {
      true
    }
    pageRoot
    userTextbox
    println("============================")
    userTextbox.isDisplayed
    println("============================")
    pageRoot.elements(By.xpath("/*"))
    pageRoot.elements(By.xpath("/*"))(0)
  }

}

object LoginPage {

  def goto()(implicit driver: WebDriver) = {
    driver.get(Config().getTestBaseUrl())
    LoginPage()
  }

}

