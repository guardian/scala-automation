package com.gu.support

import java.io.{File, FileReader, InputStreamReader, Reader}

import com.typesafe.config.ConfigFactory

class Config(localFile: Option[Reader], projectFile: Reader, frameworkFile: Reader) {

  private val config: com.typesafe.config.Config = {

    def inFileFallback(environment: String)(conf: com.typesafe.config.Config) = {
      if (conf.hasPath(environment)) conf.getObject(environment).withFallback(conf).toConfig()
      else conf
    }

    def crossFileFallback(localConf: Option[com.typesafe.config.Config], projectConf: com.typesafe.config.Config, frameworkConfig: com.typesafe.config.Config) = {
      val projWithFallback = projectConf.withFallback(frameworkConfig)
      localConf match {
        case Some(conf) => conf.withFallback(projWithFallback)
        case None => projWithFallback
      }
    }

    val frameworkConfig = ConfigFactory.parseReader(frameworkFile)
    val projectConf = ConfigFactory.parseReader(projectFile)
    val localConf = localFile.map(ConfigFactory.parseReader(_))

    val environment = crossFileFallback(localConf, projectConf, frameworkConfig).getString("environment")

    val specificEnvFallback = inFileFallback(environment)_

    crossFileFallback(localConf.map(specificEnvFallback(_)), specificEnvFallback(projectConf), specificEnvFallback(frameworkConfig))

  }

  protected def getConfigValue(key: String) = {
    config.getString(key)
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

  def getUserValue(key: String): String = {
    config.getConfig("user").getString(key)
  }

}

object Config {

  def getDefaultInject = {
    val local = new File("local.conf")
    val localOption =
      if (local.exists) Some(new FileReader(local))
      else None
    (localOption, getReader("project.conf"), getReader("framework.conf"))
  }

  // helper method
  def getReader(leafName: String) =
    new InputStreamReader(this.getClass.getClassLoader.getResourceAsStream(leafName))

  lazy val defaultLoader: Config = {
    val readers = getDefaultInject
    new Config(readers._1, readers._2, readers._3)
  }

  def apply() = defaultLoader

}
