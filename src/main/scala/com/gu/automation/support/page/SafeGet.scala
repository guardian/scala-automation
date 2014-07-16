package com.gu.automation.support.page

import org.openqa.selenium.WebElement

/**
 * Created by jduffell on 13/07/2014.
 *
 * Uses the normal implicit waits to return things about the element, returning the default value if the element
 * is not present at the end of the time.
 */
trait SafeGet {

  def elementOption(element: => WebElement): Option[WebElement] = {
    try {
      Some(element)
    } catch {
      case e: org.openqa.selenium.NoSuchElementException => None
    }

  }

}

