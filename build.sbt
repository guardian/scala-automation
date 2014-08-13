import com.typesafe.sbt.git.ConsoleGitRunner
import sbt.Keys._
import sbt._

name := "scala-automation"

organization := "com.gu.tmp"

scalaVersion := "2.10.3"

resolvers ++= Seq(
  "Guardian GitHub Releases" at "http://guardian.github.io/maven/repo-releases"
)

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

description := "Scala client for the Guardian's Content API"

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
    </developers>
  )

licenses := Seq("Apache V2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

pgpSecretRing := file("secring.gpg")

pgpPublicRing := file("pubring.gpg")


lazy val shipIt = taskKey[Unit]("ship it to the maven central").dependsOn(
  SonatypeKeys.sonatypeReleaseAll.dependsOn(PgpKeys.publishSigned))


// if it's master publish a snapshot TODO, if it's a tag publish a release, otherwise just run test
val dynamic = Def.taskDyn {
  if (buildingNewVersion.value)
    Def.task {
      val log = streams.value.log
      log.info("shipIt")
      shipIt.value
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
  ConsoleGitRunner("describe", "--match","t[0-9]*","HEAD")(file("."))
}

lazy val buildingNewVersion = settingKey[Boolean]("whether we're building a new version to ship")

buildingNewVersion := {
  git.gitCurrentTags.value.contains(latestGitTag.value)
}

credentials += Credentials("Sonatype Nexus Repository Manager",
  "oss.sonatype.org",
  "guardian.build",
  password.value)

lazy val password = settingKey[String]("the password")

password := {
  System.getenv("SONATYPE_PASSWORD")
}
