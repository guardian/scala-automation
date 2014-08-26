package com.gu.automation.support
import java.io.{File, FileReader, InputStreamReader, Reader}

import com.typesafe.config.ConfigFactory
import org.apache.commons.lang3.math.NumberUtils._

import scala.collection.JavaConversions._

class Config(localFile: Option[Reader], projectFile: Option[Reader], frameworkFile: Option[Reader]) {

  private val config: com.typesafe.config.Config = {

    def inFileFallback(environment: String)(conf: com.typesafe.config.Config) = {
      if (conf.hasPath(environment)) conf.getObject(environment).withFallback(conf).toConfig()
      else conf
    }

    def crossFileFallback(localConf: com.typesafe.config.Config, projectConf: com.typesafe.config.Config, frameworkConfig: com.typesafe.config.Config) = {
      localConf.withFallback(projectConf.withFallback(frameworkConfig))
    }

    val frameworkConfig = frameworkFile.map(ConfigFactory.parseReader(_)).getOrElse(ConfigFactory.empty())
    val projectConf = projectFile.map(ConfigFactory.parseReader(_)).getOrElse(ConfigFactory.empty())
    val localConf = localFile.map(ConfigFactory.parseReader(_)).getOrElse(ConfigFactory.empty())

    val environment = crossFileFallback(localConf, projectConf, frameworkConfig).getString("environment")

    val specificEnvFallback = inFileFallback(environment)_

    val allFiles = crossFileFallback(specificEnvFallback(localConf), specificEnvFallback(projectConf), specificEnvFallback(frameworkConfig))

    ConfigFactory.defaultOverrides().withFallback(allFiles)
  }

  protected def getConfigValue(key: String) = {
    config.getString(key)
  }

  protected def getOption(key: String) = {
    if (config.hasPath(key)) Some(config.getString(key))
    else None
  }

  def getBrowserEnvironment(): String = {
    getConfigValue("browserEnvironment")
  }

  def getProjectName(): String = {
    getConfigValue("projectName")
  }

  def getBrowsers(): List[Browser] = {
    val browsers = config.getConfigList("browsers") map { browserElement =>
      val version = if (browserElement.hasPath("version")) Some(browserElement.getString("version")).filter(isNumber) else None
      Browser(browserElement.getString("name"), version)
    }
    browsers.toList
  }

  def getPlatform(): Option[String] = {
    getOption("platform")
  }

  def getResolution(): Option[String] = {
    getOption("resolution")
  }

  def getBrowserStackOS(): Option[String] = {
    getOption("browserStackOS")
  }

  def getBrowserStackOSVersion(): Option[String] = {
    getOption("browserStackOSVersion")
  }

  def getBrowserStackVisualLog(): Option[String] = {
    getOption("browserStackVisualLog")
  }

  def getWebDriverRemoteUrl(): String = {
    getConfigValue("webDriverRemoteUrl")
  }

  def getTestBaseUrl(): String = {
    getConfigValue("testBaseUrl")
  }

  private def getOptionalUserContainer(user: Option[String]): com.typesafe.config.Config = {
    (user match {
      case None => config
      case Some(user) => config.getConfig(user)
    })
  }

  def getLoginEmail(user: Option[String] = None): String = {
    getOptionalUserContainer(user).getString("loginEmail")
  }

  def getLoginPassword(user: Option[String] = None): String = {
    getOptionalUserContainer(user).getString("loginPassword")
  }

  def getUsername(user: Option[String] = None): String = {
    getOptionalUserContainer(user).getString("username")
  }

  def isAutoAcceptSSLCert(): Boolean = {
    config.getBoolean("autoAcceptSSLCert")
  }

  def isBrowserStackLocal(): Boolean = {
    config.getBoolean("browserStackLocal")
  }

  def getIdApiRoot(): String = {
    getConfigValue("idApiRoot")
  }

  def getUserValue(key: String): String = {
    config.getConfig("user").getString(key)
  }

  def getPluginValue(key: String, default: String = ""): String = {
    if (config.hasPath(s"plugin.$key")) {
      return config.getConfig("plugin").getString(key)
    } else {
      return default
    }
  }

}

object Config {

  def apply() = defaultLoader

  private lazy val defaultLoader: Config = {
    val readers = getDefaultInject
    new Config(readers._1, readers._2, readers._3)
  }

  def getDefaultInject = {
    val local = resolveLocalConfFile()
    val localOption =
      if (local.exists) Some(new FileReader(local))
      else None
    (localOption, getReader("project.conf"), getReader("framework.conf"))
  }

  private def getReader(leafName: String) = {
    val resource = this.getClass.getClassLoader.getResourceAsStream(leafName)
    if (resource == null) None
    else Some(new InputStreamReader(resource))
  }

  def resolveLocalConfFile(): java.io.File = {
    val localConfLocation = sys.props.getOrElse("local.conf.loc", "local.conf")
    new File(s"$localConfLocation")
  }

}

case class Browser(name: String, version: Option[String])
