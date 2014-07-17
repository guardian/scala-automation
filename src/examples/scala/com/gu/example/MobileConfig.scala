package com.gu.example

import java.io.Reader

import com.gu.automation.support.Config
import com.typesafe.config.ConfigException

/**
 */
class MobileConfig(localFile: Option[Reader], projectFile: Option[Reader], frameworkFile: Option[Reader]) extends Config(localFile, projectFile, frameworkFile) {

  def getBrowserConfig(key: String): Option[String] = {
    try {
      val value = getConfigValue(getBrowser() + "." + key)
      value match {
        case "" => None
        case x => Some(x)
      }
    } catch {
      case exc: ConfigException => None
    }
  }

  def isIos(): Boolean = {
    BrowserType.withName(super.getBrowser()) == BrowserType.ios
  }

  def isAndroid() : Boolean = {
    !isIos()
  }

}

object MobileConfig {

  lazy val defaultLoader: MobileConfig = {
    val readers = Config.getDefaultInject
    new MobileConfig(readers._1, readers._2, readers._3)
  }

  def apply() = defaultLoader

}

object BrowserType extends Enumeration {
  type BrowserType = Value
  val ios, android = Value
}

