package com.gu.support

import org.scalatest._
import scala.language.experimental.macros
import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriver}

abstract class BaseTest extends fixture.FeatureSpec with ParallelTestExecution with TestRetries with fixture.TestDataFixture {

  implicit var driver: WebDriver = null
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
      withWebDriver(td.name, { (driver, logger) =>
        this.driver = driver
        this.logger = logger
        testFun
      })
    })
  }

  protected def startDriver(): WebDriver = {
    WebDriverManagement.startWebDriver(logger)
  }

  private def withWebDriver(testName: String, testFun: (WebDriver, TestLogger) => Unit) = {
    val logger = new TestLogger(testName)
    val driver = startDriver()
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