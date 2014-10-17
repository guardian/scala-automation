import com.typesafe.sbt.S3Plugin.S3._
import sbt.Keys._
import sbt._

name := "scala-automation"

organization := "com.gu"

scalaVersion := "2.10.3"

lazy val root = (project in file(".")).enablePlugins(ShipAutoPlugin)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.0",
  "org.seleniumhq.selenium" % "selenium-java" % "2.43.1",
  "com.typesafe" % "config" % "1.2.1",
  "com.google.code.findbugs" % "jsr305" % "1.3.+", // workaround for a Scala compiler bug, triggered by guava
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.2", // workaround for a Scala compiler bug, triggered by joda-time
  "com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0"
)

unmanagedSourceDirectories in Compile += baseDirectory.value / "src/examples/scala"

description := "Core Scala Automation project"

scmInfo := Some(ScmInfo(
  url("https://github.com/guardian/scala-automation"),
  "scm:git:git@github.com:guardian/scala-automation.git"
))

pomExtra := (
  <url>https://github.com/guardian/scala-automation</url>
    <developers>
      <developer>
        <id>johnduffell</id>
        <name>John Duffell</name>
        <url>https://github.com/johnduffell</url>
      </developer>
      <developer>
        <id>istvanpamer</id>
        <name>Istvan Pamer</name>
        <url>https://github.com/istvanpamer</url>
      </developer>
      <developer>
        <id>jamesoram</id>
        <name>James Oram</name>
        <url>https://github.com/jamesoram</url>
      </developer>
    </developers>
  )

licenses := Seq("Apache V2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

pgpSecretRing := file("local.secring.gpg")

pgpPublicRing := file("local.pubring.gpg")

mappings in upload ++= Seq((new java.io.File("docs/changelog.css"),"changelog.css"))

host in upload := "scala-automation.s3.amazonaws.com"
