package com.gu.automation.support

import java.io.InputStreamReader

import com.typesafe.config.ConfigFactory
import org.scalatest._

/**
 */
class ConfigTest extends FlatSpec with Matchers {

  "The Config" should "get a default value from the framework.conf without a local file" in {
    val configLoader = new Config(None, Some(getReader("project1.conf")), Some(getReader("framework1.conf")))
    configLoader.getWebDriverRemoteUrl() should be ("")
  }

  "The Config" should "get a default value from the framework.conf with a local file" in {
    val configLoader = new Config(Some(getReader("local1.conf")), Some(getReader("project1.conf")), Some(getReader("framework1.conf")))
    configLoader.getWebDriverRemoteUrl() should be ("")
  }

  it should "get a project specific value from project.conf" in {
    val configLoader = new Config(Some(getReader("local1.conf")), Some(getReader("project1.conf")), Some(getReader("framework1.conf")))
    configLoader.getTestBaseUrl() should be ("http://localhost:8080")
  }

  it should "get a local machine specific value from local.conf" in {
    val configLoader = new Config(Some(getReader("local2.conf")), Some(getReader("project1.conf")), Some(getReader("framework1.conf")))
    configLoader.getTestBaseUrl() should be ("http://www.localtest.com")
  }

  "The Config" should "be able to supply user config items" in {
    val configLoader = new Config(Some(getReader("localUserConfig.conf")), Some(getReader("project1.conf")), Some(getReader("framework1.conf")))
    configLoader.getUserValue("cheese") should be ("hello")
  }

  it should "use the project config before the framework" in {
    val configLoader = new Config(None, Some(getReader("precedenceProject.conf")), Some(getReader("precedenceFramework.conf")))
    configLoader.getUserValue("first") should be ("ProjectEnv")
    configLoader.getUserValue("second") should be ("ProjectGlobal")
    configLoader.getUserValue("third") should be ("FrameworkEnv")
    configLoader.getUserValue("fourth") should be ("FrameworkGlobal")
  }

  it should "use the local config before the project and framework" in {
    val configLoader = new Config(Some(getReader("precedenceProject.conf")), Some(getReader("precedenceFramework.conf")), Some(getReader("precedenceFramework.conf")))
    configLoader.getUserValue("first") should be ("ProjectEnv")
    configLoader.getUserValue("second") should be ("ProjectGlobal")
    configLoader.getUserValue("third") should be ("FrameworkEnv")
    configLoader.getUserValue("fourth") should be ("FrameworkGlobal")
  }

  "The Config" should "also override with system properties" in {
    System.setProperty("browser", "chrome")
    ConfigFactory.invalidateCaches()
    val configLoader = new Config(None, None, Some(getReader("framework1.conf")))
    configLoader.getBrowser() should be ("chrome")
  }

  "The Config" should "handle optional values" in {
    val configLoader = new Config(None, None, Some(getReader("framework1.conf")))
    configLoader.getPlatform() should be (None)
    configLoader.getBrowserVersion() should be (Some("12"))
  }

  // helper method
  def getReader(leafName: String) = {
    val stream = this.getClass.getResourceAsStream(leafName)
    new InputStreamReader(stream)
  }

}
