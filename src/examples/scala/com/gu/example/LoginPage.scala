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

  private lazy val root = driver >> By.xpath("/*")

  private def userTextbox = root >> By.xpath(".//*") >> By.id("ss")
  private def passwordTextbox = root >> By.xpath("//*")
  private def submitButton = root >> By.cssSelector(".form-field > button")

  private def textboxes = root >> By.id("user")

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
    if (userTextbox.safeGet != None && passwordTextbox.safeGet != None) {
      true
    }
    userTextbox.waitGet(30).click()
    userTextbox
    println("============================")
    userTextbox.isDisplayed
    println("============================")
//    val x = root.waitGet(10).>>(By.id("dd"))
//    root.elements(By.xpath("/*"))
//    root.elements(By.xpath("/*"))(0)
  }

}

object LoginPage {

  def goto()(implicit driver: WebDriver) = {
    driver.get(Config().getTestBaseUrl())
    LoginPage()
  }

}

case class TextPage(root: Element) {
  private def heading = root >> By.name("heading")
}