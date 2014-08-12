logLevel := Level.Warn

//addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8.3")

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.2")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.2.1")

resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")
