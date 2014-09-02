import com.typesafe.sbt.git.ConsoleGitRunner
import sbt.Keys._
import sbt._
import S3._

name := "scala-automation"

organization := "com.gu"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.0",
  "org.seleniumhq.selenium" % "selenium-java" % "2.42.0",
  "com.typesafe" % "config" % "1.2.1",
  "com.google.code.findbugs" % "jsr305" % "1.3.+", // workaround for a Scala compiler bug, triggered by guava
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.2"// workaround for a Scala compiler bug, triggered by joda-time
)

unmanagedSourceDirectories in Compile += baseDirectory.value / "src/examples/scala"

sonatypeSettings

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

pgpSecretRing := file("secring.gpg")

pgpPublicRing := file("pubring.gpg")


SonatypeKeys.sonatypeReleaseAll <<= SonatypeKeys.sonatypeReleaseAll.dependsOn(PgpKeys.publishSigned)

PgpKeys.publishSigned <<= PgpKeys.publishSigned.dependsOn(test in Test)

// if it's master publish a snapshot TODO, if it's a tag publish a release, otherwise just run test
val dynamic = Def.taskDyn {
  if (buildingNewVersion.value)
    Def.task {
      val log = streams.value.log
      log.info("shipIt")
      SonatypeKeys.sonatypeReleaseAll.value
      ()
    }
  else {
    Def.task {
      val log = streams.value.log
      log.info("test")
      (test in Test).value
    }
  }
}

// the version is the latest tag starting with v, however if it's not the current commit then add -SNAPSHOT on the end
version in ThisBuild := {
  val version = latestGitTag.value.substring(1)
  val versionStem = if (version.contains("-")) version.substring(0, version.indexOf("-")) else version
  versionStem + (if (buildingNewVersion.value) "" else "-SNAPSHOT")
}

lazy val travis = taskKey[Unit]("travis task")

travis := {
  val log = streams.value.log
  log.info(">>> log some values")
  log.info(s"gitCurrentBranch: ${git.gitCurrentBranch.value}")
  log.info(s"gitCurrentTags: ${git.gitCurrentTags.value}")
  //println(s"branch: ${git.branch.value}")
  log.info(s"gitHeadCommit: ${git.gitHeadCommit.value}")
  //  println(s"versionProperty: ${git.versionProperty.value}")
  log.info(s"version value: ${version.value}")
  log.info(s"latestGitTag: ${latestGitTag.value}")
  log.info(s"buildingNewVersion: ${buildingNewVersion.value}")
  log.info("<<< finished logging some values")
  dynamic.value
}

pgpPassphrase := Some(password.value.toCharArray)

// the latestGitTag is used to find out what version to publish as
lazy val latestGitTag = settingKey[String]("either v1.0 or v1.0-1-2fdd54b depends if it's on the tag")

latestGitTag := {
  ConsoleGitRunner("describe", "--match","v[0-9]*","HEAD")(file("."))
}

lazy val buildingNewVersion = settingKey[Boolean]("whether we're building a new version to ship")

buildingNewVersion := {
  val tagsOnHead = git.gitCurrentTags.value
  tagsOnHead.contains(latestGitTag.value)
}

credentials += Credentials("Sonatype Nexus Repository Manager",
  "oss.sonatype.org",
  "guardian.build",
  password.value)

lazy val password = settingKey[String]("the password")

password := {
  val password = System.getenv("SONATYPE_PASSWORD")
  if (password == null) ""
  else password
}

s3Settings

mappings in upload := Seq((new java.io.File("docs/local.changelog.html"),"changelog.html"),(new java.io.File("docs/changelog.css"),"changelog.css"))

host in upload := "scala-automation.s3.amazonaws.com"

credentials += Credentials(new File("local.s3credentials.properties"))

SonatypeKeys.sonatypeReleaseAll <<= SonatypeKeys.sonatypeReleaseAll.dependsOn(upload)

upload <<= upload.dependsOn(changeLog)
