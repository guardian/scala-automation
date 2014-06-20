package com.gu.automation.core

import java.io._

import com.gu.automation.support.TestLogging
import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriver}
import org.scalatest._

import scala.language.experimental.macros


abstract class BaseTest[T <: WebDriver] extends fixture.FeatureSpec with ParallelTestExecution with fixture.TestDataFixture with TestLogging {

  implicit var driver: T

  def given[A](body: => A) = {
    logger.setPhase("GIVEN")
    new WhenOrThen(body)
  }

  class WhenOrThen[A](val input: A) {

    def when[B](body: A => B): WhenOrThen[B] = {
      logger.setPhase("WHEN")
      new WhenOrThen(body(input))
    }

    def then[B](body: A => B): WhenOrThen[B] = {
      logger.setPhase("THEN")
      new WhenOrThen(body(input))
    }

  }

  protected def scenarioWeb(specText: String, testTags: Tag*)(testFun: => Any) {
    scenario(specText, testTags:_*)({ td =>
      logger.info(td.name)
      driver = startDriver()
      try {
        testFun
      } catch {
        case e: Exception => failWithScreenshot(td.name, driver, e)
      } finally {
        driver.quit()
      }
    })
  }

  protected def startDriver(): T

  private def failWithScreenshot(testName: String, driver: WebDriver, e: Exception) = {
    logger.failure("Test failed")
    try {
      val screenshotFile = driver match {
        case ts: TakesScreenshot => {
          logger.failure("Test failed")
          ts.getScreenshotAs(OutputType.BYTES)
        }
        case _ => throw new RuntimeException("Error getting screenshot")
      }

      new File("target/test-reports").mkdirs()
      val file = new FileOutputStream(s"logs/screenshots/${testName}.png")
      file.write(screenshotFile)
      file.close
    } catch {
      case e: Exception => logger.step("Error taking screenshot.")
    }
    throw e
  }

}