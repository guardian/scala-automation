package com.gu.automation.support.page

import org.openqa.selenium.WebElement

/**
 *
 * Uses the normal implicit waits to return things about the element, returning the default value if the element
 * is not present at the end of the time.
 */
object ElementOption {

  def apply(element: => WebElement): Option[WebElement] = {
    try {
      Some(element)
    } catch {
      case e: org.openqa.selenium.NoSuchElementException => None
    }

  }

}

