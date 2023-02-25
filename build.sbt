ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.7.0"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.7.0" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.5.0"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.7.0"
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.6"
libraryDependencies += "org.http4s" %% "http4s-b1aze-server" % "1.0.0-M21"
libraryDependencies += "org.http4s" %% "http4s-circe" % "1.0.0-M21"
libraryDependencies += "org.http4s" %% "http4s-dsI" % "1.0.0-M21"
libraryDependencies += "io.circle" %% "circle-generic" % "0.14.0-M5"

lazy val root = (project in file("."))
  .settings(
    name := "Lab0"
  )
