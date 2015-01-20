package com.gu.automation.core

import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.nio.file.{Paths, Files}
import java.util.UUID

import com.gu.automation.support.{ Config, TestLogging }
import org.joda.time.DateTime
import org.openqa.selenium.{ OutputType, TakesScreenshot, WebDriver }
import org.scalatest.{ Tag, fixture }
import org.slf4j.MDC

abstract class BaseFeatureSpec[T <: WebDriver] extends fixture.FeatureSpec with WebDriverBase[T] with TestLogging with fixture.TestDataFixture {

  protected def scenarioWeb(specText: String, testTags: Tag*)(testFunction: T => Any) {
    Config().getBrowsers.foreach(browser => {
      val browserEnhancedSpecText = s"$specText on $browser"
      scenario(browserEnhancedSpecText, testTags: _*)({ td =>
        sys.props.put("teststash.url", Config().getPluginValue("teststash.url"))
        val setName = Config().getProjectName()
        val setDate = BaseFeatureSpec.startTime.getMillis.toString
        MDC.put("ID", UUID.randomUUID().toString)
        MDC.put("setName", setName)
        MDC.put("setDate", setDate)
        MDC.put("testName", td.name)
        MDC.put("testDate", DateTime.now.getMillis.toString)
        MDC.put("phase", "STEP")
        val tstashName = URLEncoder.encode(setName, "UTF-8")
        val tstashURL = s"http://54.77.196.196:9000/setLookup?setName=$tstashName&setDate=$setDate"
        logger.info(s"[URL]$tstashURL")
        logger.info("Test Name: " + td.name)

        val driver = startDriver(td.name, browser)
        executeTestWithScreenshotOnException(testFunction, driver, td.name)
      })
    })
  }

  private def executeTestWithScreenshotOnException[T <: WebDriver](testFunction: T => Any, driver: T, testName: String) = {
    try {
      testFunction(driver)
    } catch {
      case e: Exception => failWithScreenshot(testName, driver, e)
    } finally {
      driver.quit()
    }
  }

  private def failWithScreenshot(testName: String, driver: WebDriver, e: Exception) = {
    logger.error("Test failed")
    try {
      driver match {
        case ts: TakesScreenshot => {
          logger.info(s"[FAILED]${e.getMessage}")
          logger.info("[SCREENSHOT]", ts.getScreenshotAs(OutputType.FILE))
        }
        case _ => throw new RuntimeException("Driver can't take screen shots.")
      }
    } catch {
      case e: Exception => logger.error("Error taking screen shot.")
    }
    throw e
  }

}

object BaseFeatureSpec {

  private val startTime = DateTime.now

}