name := "qafenix"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.sun.jersey" % "jersey-core" % "1.9"
)     

play.Project.playJavaSettings
