package com.gu.example

import com.gu.automation.support.Config
import com.gu.automation.support.page.{SafeFindBoolean, SafeGet, WaitGet}
import org.openqa.selenium.By._
import org.openqa.selenium.{By, WebDriver, WebElement}

/**
 * Created by ipamer on 02/06/2014.
 */
case class LoginPage(implicit driver: WebDriver) {

  implicit def asdf(testAttribute: String) = By.cssSelector(s"asdasd${testAttribute}sda")

  private def root = driver.findElement(xpath("/div[@rt='ad']"))

//  private def userTextbox = root.element(xpath("fish"))
//  private def passwordTextbox = root element xpath("./*")
  private def submitButton = root findElement cssSelector(".form-field > button")
//
//  private def textboxes = root element id("user")

  def login(user: String, password: String): LoginPage = {
//    Wait().until(ExpectedConditions.elementToBeClickable(userTextbox))
//    userTextbox.sendKeys(user)
//    passwordTextbox.sendKeys(password)
//    passwordTextbox.sendKeys(password)
//    submitButton.click()
//
//    userTextbox.safeGet.map(_.isDisplayed) match {
//      case Some(true) => // do something happy
//      case _ => throw new RuntimeException("can't find")
//    }
//
//    if (textboxes.filter(_.isDisplayed == true).length > 2) {
//      // do something happy
//    } else {
//      throw new RuntimeException("can't find")
//    }
//
//    textboxes.map(TextPage(_))
//    TextPage(userTextbox)
//
    if (SafeFindBoolean(submitButton.isDisplayed)) {
      //happy
    } else {
      // sad
    }
    
    SafeGet(root) match {
      case None => ///eeek
      case Some(x) => // happy days
    }

    this
  }
//  def waitGet(timeOutInSeconds: Long = 30)(implicit driver: WebDriver) = {
//    new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.presenceOfElementLocated(locator))
//    get
//  }


  def test() = {
    println("1============================")
    WaitGet(root).isDisplayed
//    if (userTextbox.safeGet != None && passwordTextbox.safeGet != None) {
//      true
//    }
//    println("2============================")
////    userTextbox.waitGet(30).click()
//    userTextbox
//    println("3============================")
//    userTextbox.isDisplayed
//    println("4============================")
////    val x = root.waitGet(10).>>(By.id("dd"))
//    root.element(By.xpath("/*"))
//    println("5============================")
//    root.element(By.xpath("/*"))(0)
    println("6============================")
  }

}

object LoginPage {

  def goto()(implicit driver: WebDriver) = {
    driver.get(Config().getTestBaseUrl())
    LoginPage()
  }

}

case class TextPage(root: WebElement) {
  private def heading = root findElement name("heading")
}

