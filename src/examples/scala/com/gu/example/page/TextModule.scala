package com.gu.example.page

import org.openqa.selenium.By._
import org.openqa.selenium.WebElement

/**
 */
case class TextModule(root: WebElement) {
  private def heading = root findElement name("heading")

  def getHeading = heading.getText
}

