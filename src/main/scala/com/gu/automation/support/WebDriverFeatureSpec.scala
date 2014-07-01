package com.gu.automation.support

import java.io.{File, FileOutputStream}
import java.util.UUID

import com.gu.automation.core.{WebDriverBase, WebDriverManagement}
import org.joda.time.DateTime
import org.openqa.selenium.{OutputType, TakesScreenshot, WebDriver}
import org.scalatest.{ParallelTestExecution, Tag, fixture}
import org.slf4j.MDC


class WebDriverFeatureSpec extends fixture.FeatureSpec with WebDriverBase[WebDriver] with TestLogging with ParallelTestExecution with fixture.TestDataFixture {

  override implicit var driver: WebDriver = null

  protected def startDriver(): WebDriver = {
    WebDriverManagement.startWebDriver()
  }

  protected def scenarioWeb(specText: String, testTags: Tag*)(testFun: => Any) {
    scenario(specText, testTags: _*)({ td =>
      sys.props.put("teststash.url", Config().getPluginValue("teststash.url"))
      logger.info("[TEST START]") // starts websocket to T-Stash
      logger.info("Test Name: " + td.name)
      MDC.put("ID", UUID.randomUUID().toString)
      MDC.put("setName", Config().getProjectName())
      MDC.put("setDate", Config().getTestSetStartTime().getMillis.toString)
      MDC.put("testName", td.name)
      MDC.put("testDate", DateTime.now.getMillis.toString)

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
