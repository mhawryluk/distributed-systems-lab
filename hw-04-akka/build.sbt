name := "hw-04-akka"

version := "0.1"

scalaVersion := "2.13.8"

idePackagePrefix := Some("homework.akka")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-typed" % "2.6.19",
  "com.typesafe.akka" %% "akka-http" % "10.2.9",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.19",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.2.9",
)
