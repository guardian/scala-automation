package com.gu.automation.support

import java.io.{File, FileReader, InputStreamReader, Reader}

import com.typesafe.config.ConfigFactory
import org.joda.time.DateTime
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

  def getProjectName(): String = {
    getConfigValue("projectName")
  }

  def getTestSetStartTime(): DateTime = {
    Config.startTime
  }

  def getBrowser(): String = {
    getConfigValue("browser")
  }

  def getWebDriverRemoteUrl(): String = {
    getConfigValue("webDriverRemoteUrl")
  }

  def getTestBaseUrl(): String = {
    getConfigValue("testBaseUrl")
  }

  def getLoginEmail(user: Option[String]): String = {
    (user match {
      case None => config
      case Some(user) => config.getConfig(user)
    }).getString("loginEmail")
  }

  def getLoginPassword(user: Option[String]): String = {
    (user match {
      case None => config
      case Some(user) => config.getConfig(user)
    }).getString("loginPassword")
  }

  def getIdApiRoot(): String = {
    getConfigValue("idApiRoot")
  }

  def getUserValue(key: String): String = {
    config.getConfig("user").getString(key)
  }

  def getCapabilities(): Option[List[(String, String)]] = {
    if (config.hasPath("capabilities")) {
      val caps = config.getConfig("capabilities")
      Some(caps.entrySet().map(e => (e.getKey, caps.getString(e.getKey))).toList)
    } else {
      None
    }
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

  private val startTime = DateTime.now

  private lazy val defaultLoader: Config = {
    val readers = getDefaultInject
    new Config(readers._1, readers._2, readers._3)
  }

  def getDefaultInject = {
    val local = new File("local.conf")
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

}
