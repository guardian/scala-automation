package com.gu.support

import org.scalatest._
import java.io.{InputStreamReader, File}

/**
 * Created by jduffell on 09/06/2014.
 */
class ConfigLoaderTest extends FlatSpec with Matchers {

  "The Config Loader" should "get a default value from the framework.conf without a local file" in {
    val configLoader = new ConfigLoader(None, getReader("project1.conf"), getReader("framework1.conf"))
    configLoader.getWebDriverRemoteUrl() should be ("")
  }

  "The Config Loader" should "get a default value from the framework.conf with a local file" in {
    val configLoader = new ConfigLoader(Some(getReader("local1.conf")), getReader("project1.conf"), getReader("framework1.conf"))
    configLoader.getWebDriverRemoteUrl() should be ("")
  }

  it should "get a project specific value from project.conf" in {
    val configLoader = new ConfigLoader(Some(getReader("local1.conf")), getReader("project1.conf"), getReader("framework1.conf"))
    configLoader.getTestBaseUrl() should be ("http://localhost:8080")
  }

  it should "get a local machine specific value from local.conf" in {
    val configLoader = new ConfigLoader(Some(getReader("local2.conf")), getReader("project1.conf"), getReader("framework1.conf"))
    configLoader.getTestBaseUrl() should be ("http://www.localtest.com")
  }

  // helper method
  def getReader(leafName: String) = {
    val stream = this.getClass.getResourceAsStream(leafName)
    new InputStreamReader(stream)
  }

}
