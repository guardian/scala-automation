package com.gu.support

import com.typesafe.config.{Config, ConfigFactory}
import java.io.{FileReader, InputStreamReader, Reader, File}

class ConfigLoader(localFile: Option[Reader], projectFile: Reader, frameworkFile: Reader) {

  private val config: Config = {
    val conf = ConfigFactory.parseReader(projectFile).withFallback(ConfigFactory.parseReader(frameworkFile))
    val withLocalOverrides = localFile match {
      case Some(localReader) => ConfigFactory.parseReader(localReader).withFallback(conf)
      case None => conf
    }
    withLocalOverrides.getObject(withLocalOverrides.getString("environment")).withFallback(withLocalOverrides).toConfig()
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

}

object ConfigLoader {

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

  lazy val defaultLoader: ConfigLoader = {
    val readers = getDefaultInject
    new ConfigLoader(readers._1, readers._2, readers._3)
  }

  def apply() = defaultLoader

}
