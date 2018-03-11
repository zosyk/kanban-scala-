name := "weKanban"

version := "1.0"
scalaVersion := "2.11.8"
libraryDependencies ++= Seq(
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"
  , "org.scalaz" %% "scalaz-core" % "7.2.20"
  , "com.h2database" % "h2" % "1.2.137"
  , "org.squeryl" % "squeryl_2.10" % "0.9.5-6"

)

enablePlugins(JettyPlugin)