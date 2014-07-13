package com.gu.automation.support.page

import org.openqa.selenium.WebElement

/**
 * Created by jduffell on 13/07/2014.
 *
 * Uses the normal implicit waits to return things about the element, returning the default value if the element
 * is not present at the end of the time.
 */
object SafeGet {

  private def apply[B]
  (get: WebElement => B, default: B)
  (element: => WebElement): B = {
    try {
      get(element)
    } catch {
      case e: org.openqa.selenium.NoSuchElementException => default
    }

  }

  private def boolean(get: WebElement => Boolean) = apply(get, false)_

  def isDisplayed = boolean(e=>e.isDisplayed)

  def isPresent = boolean(e => true)

  def elementOption = apply(e => Some(e), None)_
}

