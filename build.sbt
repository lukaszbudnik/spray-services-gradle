import sbt._
import sbt.Keys._
import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

version := "0.0.1"
 
scalaVersion := "2.10.4"

resolvers += "Kamon repo" at "http://repo.kamon.io"

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies += "io.spray" % "spray-can" % "1.3.1"

libraryDependencies += "io.spray" % "spray-httpx" % "1.3.1"

libraryDependencies += "io.spray" % "spray-routing" % "1.3.1"

libraryDependencies += "io.spray" % "spray-testkit" % "1.3.1" % "test"

libraryDependencies += "io.kamon" %% "kamon-core" % "0.3.1"

libraryDependencies += "io.kamon" %% "kamon-spray" % "0.3.1"

libraryDependencies += "io.kamon" %% "kamon-statsd" % "0.3.1"

libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.2.5"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.0"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "test"

libraryDependencies += "org.specs2" %% "specs2-core" % "2.3.12" % "test"

libraryDependencies += "org.specs2" %% "specs2-junit" % "2.3.12" % "test"

libraryDependencies += "org.aspectj" % "aspectjweaver" % "1.7.4" % "runtime"
