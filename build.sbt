name := "scala_msngr"
 
version := "1.0" 
      
lazy val `scala_msngr` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  jdbc , ehcache , ws , specs2 % Test , guice,
  "org.mongodb" % "casbah_2.12" % "3.1.1",
  "org.slf4j" % "slf4j-simple" % "1.6.4",
  "com.google.code.gson" % "gson" % "2.8.2"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )