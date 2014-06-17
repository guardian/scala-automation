package com.gu.support

import java.io._

import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriver}
import org.scalatest._

import scala.language.experimental.macros


abstract class BaseTest[T <: WebDriver] extends fixture.FeatureSpec with ParallelTestExecution with fixture.TestDataFixture {

  implicit var driver: T
  implicit var logger: TestLogger = null

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
      withWebDriver(td.name, {
        testFun
      })
    })
  }

  protected def startDriver(): T

  private def withWebDriver(testName: String, testFun: => Unit) = {
    logger = new TestLogger(testName)
    driver = startDriver()
    try {
      testFun
    } catch {
      case e: Exception => failWithScreenshot(testName, driver, logger, e)
    } finally {
      driver.quit()
      logger.dumpMessages()
    }
  }

  private def failWithScreenshot(testName: String, driver: WebDriver, logger: TestLogger, e: Exception) = {
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
      val file = new FileOutputStream(s"target/test-reports/${testName}.png")
      file.write(screenshotFile)
      file.close
    } catch {
      case e: Exception => logger.log("Error taking screenshot.")
    }
    throw e
  }

}