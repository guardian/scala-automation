package com.gu.support

import com.typesafe.config.{Config, ConfigFactory}

object ConfigLoader {

  private val config: Config = {
    val conf = ConfigFactory.load()
    val envConf = conf.getObject(conf.getString("environment")).withFallback(conf).toConfig()
//    println(envConf.root().render())
    envConf
  }

  protected def getConfigValue(key: String) = {
    config.getString(key)
  }

  def getBrowser(): String = {
    config.getString("browser")
  }

  def getWebDriverRemoteUrl(): String = {
    config.getString("webDriverRemoteUrl")
  }

  def getTestBaseUrl(): String = {
    config.getString("testBaseUrl")
  }

}
