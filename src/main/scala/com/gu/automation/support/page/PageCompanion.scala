package com.gu.automation.support.page

import com.gu.automation.support.Config
import org.openqa.selenium.WebDriver

trait PageCompanion[A] {

  val relativeUrl: String = ""

  protected def makePage(implicit driver: WebDriver): A

  def goto(urlAppend: String = "")(implicit driver: WebDriver): A = {
    driver.get(Config().getTestBaseUrl() + relativeUrl + urlAppend)
    makePage
  }

}
