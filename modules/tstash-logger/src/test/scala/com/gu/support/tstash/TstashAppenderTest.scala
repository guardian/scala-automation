package com.gu.automation.api

import java.util.UUID

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.joda.time.DateTime
import org.scalatest._
import org.slf4j.MDC

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by jduffell on 12/06/2014.
 */
class TstashAppenderTest extends FlatSpec with Matchers with LazyLogging {

  "The auth api" should "let us log in as a valid user" in {

//    sys.props.put("teststash.url", "ws://localhost:9000/report")
    sys.props.put("teststash.url", "ws://10.252.93.148:8081/report")
    MDC.put("ID", UUID.randomUUID().toString)
    MDC.put("testName", "test name 1")
    MDC.put("testDate", DateTime.now.getMillis.toString)
    MDC.put("setName", "set name 1")
    MDC.put("setDate", SetTime.time.getMillis.toString)
    println("setDate: " + SetTime.time.getMillis.toString)

    logger.info("[TEST START]")
    logger.info("Test message 1.")
    logger.info("Test message 2.")
    logger.info("[TEST END]")

  }

  "The auth api 222" should "let us log in as a valid 222 user" in {

//    sys.props.put("teststash.url", "ws://localhost:9000/report")
    sys.props.put("teststash.url", "ws://10.252.93.148:8081/report")
    MDC.put("ID", UUID.randomUUID().toString)
    MDC.put("testName", "test name 2")
    MDC.put("testDate", DateTime.now.getMillis.toString)
    MDC.put("setName", "set name 1")
    MDC.put("setDate", SetTime.time.getMillis.toString)
    println("setDate: " + SetTime.time.getMillis.toString)

    logger.info("[TEST START]")
    logger.info("Test message 1111.")
    logger.info("Test message 2222222.")
    logger.info("[TEST END]")

  }

}

object SetTime {
  val time = DateTime.now
}