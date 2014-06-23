web-automation-core-scala-api
-----------------------------
To use this just edit your build.sbt as follows:

libraryDependencies ++= Seq(
  "com.gu" %% "web-automation-core-scala-api" % "1.0-SNAPSHOT"
)

AuthApi
-------
From your code, you can import com.gu.automation.api.AuthApi and then do something like

    val future = AuthApi.authenticate(email, password)