import sbt.Keys._
import sbtrelease._
import ReleaseStateTransformations._

name := "web-automation-core-scala-api"

organization := "com.gu"

scalaVersion := "2.10.3"

resolvers ++= Seq(
  "Guardian GitHub Releases" at "http://guardian.github.io/maven/repo-releases"
)

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.0",
  "net.databinder.dispatch" % "dispatch-core" % "0.11.1",
  "net.liftweb" % "lift-json" % "2.0"
)

releaseSettings

sonatypeSettings

description := "Scala client for the Guardian's Content API"

scmInfo := Some(ScmInfo(
  url("https://github.com/guardian/scala-automation/modules/api-client"),
  "scm:git:git@github.com:guardian/scala-automation.git"
))

pomExtra := (
  <url>https://github.com/guardian/scala-automation</url>
    <developers>
      <developer>
        <id>istvanpamer</id>
        <name>Istvan Pamer</name>
        <url>https://github.com/istvanpamer</url>
      </developer>
    </developers>
  )

licenses := Seq("Apache V2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

ReleaseKeys.crossBuild := true

ReleaseKeys.releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,// new
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep( // instead of publishArtifacts
    action = state => Project.extract(state).runTask(PgpKeys.publishSigned, state)._1,
    enableCrossBuild = true
  ),
  setNextVersion,
  commitNextVersion,
  ReleaseStep(state => Project.extract(state).runTask(SonatypeKeys.sonatypeReleaseAll, state)._1),// new
  pushChanges
)
