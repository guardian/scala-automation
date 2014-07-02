package com.gu.automation.support

import com.typesafe.scalalogging.slf4j.Logger
import org.slf4j.LoggerFactory

trait TestLogging {
  protected implicit lazy val logger: TestLogger = new TestLogger(Logger(LoggerFactory getLogger getClass.getName))
}

class TestLogger(val logger: Logger) extends com.typesafe.scalalogging.Logger {

  private var phase: String = "GIVEN"

  def setPhase(phase: String) {
    this.phase = phase
  }

  def step(str: String) {
    info(phase + " " + str)
    setPhase("AND")
  }

  def assertion(msg: String) {
    info("ASSERT: " + msg)
  }

  def failure(str: String) {
    error(str)
  }

  def driver(msg: String) {
    info("DRIVER: " + msg)
  }

  // Standard log interface
  override def error(message: String): Unit = logger.error(message)
  override def error(message: String, cause: Throwable): Unit = logger.error(message, cause)
  override def error(message: String, args: AnyRef*): Unit = logger.error(message, args)
  override def warn(message: String): Unit = logger.warn(message)
  override def warn(message: String, cause: Throwable): Unit = logger.warn(message, cause)
  override def warn(message: String, args: AnyRef*): Unit = logger.warn(message, args)
  override def info(message: String): Unit = logger.info(message)
  override def info(message: String, cause: Throwable): Unit = logger.info(message, cause)
  override def info(message: String, args: AnyRef*): Unit = logger.info(message, args)
  override def debug(message: String): Unit = logger.debug(message)
  override def debug(message: String, cause: Throwable): Unit = logger.debug(message, cause)
  override def debug(message: String, args: AnyRef*): Unit = logger.debug(message, args)
  override def trace(message: String): Unit = logger.trace(message)
  override def trace(message: String, cause: Throwable): Unit = logger.trace(message, cause)
  override def trace(message: String, args: AnyRef*): Unit = logger.trace(message, args)
}
