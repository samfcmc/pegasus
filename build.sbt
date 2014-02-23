name := "qafenix"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.sun.jersey" % "jersey-core" % "1.9",
  "mysql" % "mysql-connector-java" % "5.1.18",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)     

play.Project.playJavaSettings
