package com.gu.support

import org.scalatest._
import scala.language.experimental.macros
import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriver}
import com.gu.test.TestLogger

abstract class BaseTest extends fixture.FeatureSpec with BaseSteps with ParallelTestExecution with TestRetries with fixture.TestDataFixture {

  def given() {
    logger.setPhase("GIVEN")
  }

  def when() {
    logger.setPhase("WHEN")
  }

  def then() {
    logger.setPhase("THEN")
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

  private def withWebDriver(testName: String, testFun: (WebDriver, TestLogger) => Unit) = {
    val logger = new TestLogger(testName)
    val driver = WebDriverManagement.startWebDriver(logger)
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