package com.gu.automation.support

import java.io.InputStreamReader
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfterEach

/**
 */
class ConfigTest extends FlatSpec with Matchers with BeforeAndAfterEach {
  
  override def afterEach() ={
    System.clearProperty("testBaseUrl")
    System.clearProperty("local.conf.loc")
    ConfigFactory.invalidateCaches()
  }

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
    System.setProperty("testBaseUrl", "http://www.google.com")
    ConfigFactory.invalidateCaches()
    val configLoader = new Config(None, None, Some(getReader("framework1.conf")))
    configLoader.getTestBaseUrl() should be ("http://www.google.com")
  }

  "The Config" should "be able to load a custom override local.conf" in {
    System.setProperty("local.conf.loc", "src/test/systemOverride.conf")
    Config.resolveLocalConfFile().getName should be("systemOverride.conf")
  }

  "The Config" should "handle optional values" in {
    val configLoader = new Config(None, None, Some(getReader("framework1.conf")))
    configLoader.getPlatform() should be (None)
  }

  "The Config" should "load default login data" in {
    val configLoader = new Config(None, None, Some(getReader("framework1.conf")))
    configLoader.getLoginEmail() should be ("test@guardian.co.uk")
    configLoader.getLoginPassword() should be ("testpwd")
    configLoader.getUsername() should be ("testuser")
  }

  "The Config" should "load named login data" in {
    val configLoader = new Config(None, None, Some(getReader("multipleuser.conf")))
    configLoader.getLoginEmail(Some("User1")) should be ("test@guardian.co.uk")
    configLoader.getLoginPassword(Some("User1")) should be ("testpwd")
    configLoader.getUsername(Some("User1")) should be ("testuser")
  }

  "The Config" should "load default subscription data" in {
    val configLoader = new Config(None, None, Some(getReader("framework1.conf")))
    configLoader.getPrintSubscriptionCode() should be ("GUTEST")
    configLoader.getPrintSubscriptionPostCode() should be ("N1 9GU")
  }

  "The Config" should "load named subscription data" in {
    val configLoader = new Config(None, None, Some(getReader("multipleuser.conf")))
    configLoader.getPrintSubscriptionCode(Some("User1")) should be ("GUTEST")
    configLoader.getPrintSubscriptionPostCode(Some("User1")) should be ("N1 9GU")
  }

  "The Config" should "handle list of browser objects" in {
    val configLoader = new Config(None, None, Some(getReader("framework1.conf")))
    configLoader.getBrowsers should be(List(Browser("firefox", Some("30")), Browser("chrome", Some("35"))))
  }

  "The Config" should "handle browser with no version" in {
    val configLoader = new Config(None, None, Some(getReader("browserNoVersion.conf")))
    configLoader.getBrowsers should be(List(Browser("firefox", None)))
  }

  // helper method
  def getReader(leafName: String) = {
    val stream = this.getClass.getResourceAsStream(leafName)
    new InputStreamReader(stream)
  }

}
