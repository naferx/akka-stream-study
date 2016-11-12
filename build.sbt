name := "akka-streams-study"

version := "1.0"

scalaVersion := "2.11.8"

val akkaVersion = "2.4.12"

libraryDependencies ++= Seq(
  // akka
  "com.typesafe.akka"    %%   "akka-slf4j"          %   akkaVersion,
  "com.typesafe.akka"    %%   "akka-stream"         %   akkaVersion,
  // utils
  "ch.qos.logback"       %    "logback-classic"     %   "1.1.7",

  // testing
  "com.typesafe.akka"    %%   "akka-stream-testkit" %   akkaVersion,
  "org.scalatest"        %%   "scalatest"           %   "3.0.0"
)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
