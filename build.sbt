scalaVersion := "3.1.3"
organization := "dev.zio"
name := "zio-microstream"

libraryDependencies += "dev.zio" %% "zio" % "2.0.2"
libraryDependencies += "dev.zio" %% "zio-streams" % "2.0.2"
libraryDependencies += "one.microstream" % "microstream-storage-embedded"    % "07.01.00-MS-GA"
libraryDependencies += "dev.zio" %% "zio-test"    % "2.0.2"

