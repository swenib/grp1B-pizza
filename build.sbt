name := """grp1b-pizza""" // please change me

version := "Milestone2" // please change me later

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature",
  "-Xfatal-warnings")

resolvers += "Scalaz Bintray Repo" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.specs2" %% "specs2-scalacheck" % "3.5" % "test",
  "org.specs2" %% "specs2-junit" % "3.5" % "test",
  "org.specs2" %% "specs2-mock" % "3.5" % "test",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)