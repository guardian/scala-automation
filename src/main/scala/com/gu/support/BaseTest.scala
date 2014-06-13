package com.gu.support

import org.scalatest._
import scala.language.experimental.macros
import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriver}

abstract class BaseTest[T <: WebDriver] extends fixture.FeatureSpec with ParallelTestExecution with fixture.TestDataFixture {

  class Loggable(logger: TestLogger) {

    class Given[S <: BaseSteps](steps: S) {

      def given[A](body: S => A) = {
        logger.setPhase("GIVEN")
        new WhenOrThen(body(steps))
      }

    }

    class WhenOrThen[A](input: A) {

      def when[B](body: A => B): WhenOrThen[B] = {
        logger.setPhase("WHEN")
        new WhenOrThen(body(input))
      }

      def then[B](body: A => B): WhenOrThen[B] = {
        logger.setPhase("THEN")
        new WhenOrThen(body(input))
      }

    }

  }

  protected def scenarioWeb[S <: BaseSteps](specText: String, steps: (WebDriver, TestLogger) => S, testTags: Tag*)(testFun: Loggable#Given[S] => Any) {
    scenario(specText, testTags:_*)({ td =>
      withWebDriver(td.name, { (driver, logger) =>
        val loggable = new Loggable(logger)
        val given = new loggable.Given(steps(driver, logger))
        testFun(given)
      })
    })
  }

  protected def startDriver(logger: TestLogger): T

  private def withWebDriver(testName: String, testFun: (T, TestLogger) => Unit) = {
    val logger = new TestLogger(testName)
    val driver = startDriver(logger)
    try {
      testFun(driver, logger)
    } catch {
      case e: Exception => failWithScreenshot(driver, logger, e)
    } finally {
      driver.quit()
      logger.dumpMessages()
    }
  }

  private def failWithScreenshot(driver: WebDriver, logger: TestLogger, e: Exception) = {
    val screenshotFile = driver match {
      case ts: TakesScreenshot => {
          logger.failure("Test failed")
          ts.getScreenshotAs(OutputType.FILE)
        }
      case _ => throw new RuntimeException("Error getting screenshot")
      }
      throw e
    }

}