package com.gu.example

import com.gu.automation.support.Config
import com.gu.automation.support.page.Element._
import com.gu.automation.support.page.{Element, Wait}
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.{By, WebDriver}
import By._

/**
 * Created by ipamer on 02/06/2014.
 */
case class LoginPage(implicit driver: WebDriver) {

  implicit def asdf(testAttribute: String) = By.cssSelector(s"asdasd${testAttribute}sda")

  private lazy val root = driver element cssSelector("/div[@rt='ad']")

  private def userTextbox = root.element(xpath("fish"))
  private def passwordTextbox = root element xpath("./*")
  private def submitButton = root element cssSelector(".form-field > button")

  private def textboxes = root element id("user")

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

    textboxes.map(TextPage(_))
    TextPage(userTextbox)

    this
  }

  def test() = {
    println("1============================")
    if (userTextbox.safeGet != None && passwordTextbox.safeGet != None) {
      true
    }
    println("2============================")
//    userTextbox.waitGet(30).click()
    userTextbox
    println("3============================")
    userTextbox.isDisplayed
    println("4============================")
//    val x = root.waitGet(10).>>(By.id("dd"))
    root.element(By.xpath("/*"))
    println("5============================")
    root.element(By.xpath("/*"))(0)
    println("6============================")
  }

}

object LoginPage {

  def goto()(implicit driver: WebDriver) = {
    driver.get(Config().getTestBaseUrl())
    LoginPage()
  }

}

case class TextPage(root: Element) {
  private def heading = root element By.name("heading")
}