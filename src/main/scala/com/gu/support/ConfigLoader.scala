package com.gu.support

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import scala.io.Source

class ConfigLoader {

  val config = {
    val config = loadConfigFile
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.readValue[Map[String, Object]](config)
  }

  private def loadConfigFile: String = Source.fromFile("default_config.json").getLines().mkString
}
