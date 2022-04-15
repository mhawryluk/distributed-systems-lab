name := "hw-04-akka"

version := "0.1"

scalaVersion := "2.13.8"

idePackagePrefix := Some("homework.akka")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.6.19"
)
