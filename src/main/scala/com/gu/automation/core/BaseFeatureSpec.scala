package com.gu.automation.core

import java.io.{File, FileOutputStream}
import java.util.UUID

import com.gu.automation.support.{Config, TestLogging}
import org.joda.time.DateTime
import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriver}
import org.scalatest.{ParallelTestExecution, Tag, fixture}
import org.slf4j.MDC


abstract class BaseFeatureSpec[T <: WebDriver] extends fixture.FeatureSpec with WebDriverBase[T] with TestLogging with ParallelTestExecution with fixture.TestDataFixture {

  protected def scenarioWeb(specText: String, testTags: Tag*)(testFun: => Any) {
    scenario(specText, testTags: _*)({ td =>
      sys.props.put("teststash.url", Config().getPluginValue("teststash.url"))
      MDC.put("ID", UUID.randomUUID().toString)
      MDC.put("setName", Config().getProjectName())
      MDC.put("setDate", Config().getTestSetStartTime().getMillis.toString)
      MDC.put("testName", td.name)
      MDC.put("testDate", DateTime.now.getMillis.toString)
      logger.info("[TEST START]") // starts websocket to T-Stash
      logger.info("Test Name: " + td.name)

      driver = startDriver()
      try {
        testFun
      } catch {
        case e: Exception => failWithScreenshot(td.name, driver, e)
      } finally {
        logger.info("[TEST END]") // closes websocket to T-Stash
        driver.quit()
      }
    })
  }

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

      val screenshotDir = "logs/screenshots"
      new File(screenshotDir).mkdirs()
      val file = new FileOutputStream(s"${screenshotDir}/${testName}.png")
      file.write(screenshotFile)
      file.close
    } catch {
      case e: Exception => logger.step("Error taking screenshot.")
    }
    throw e
  }

}
