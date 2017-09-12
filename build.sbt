name := """play-docker-showcase"""
organization := "com.github.dpfeiffer"

version := "1.0"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, sbtdocker.DockerPlugin, JavaAppPackaging)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.1" % Test

//sbt-assembly + sbt-docker
mainClass in assembly := Some("play.core.server.ProdServerStart")
fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)
assemblyMergeStrategy in assembly := {
  case x if x.endsWith("reference-overrides.conf") => MergeStrategy.concat
  case x                                           => (assemblyMergeStrategy in assembly).value(x)
}

dockerfile in docker := {
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("java")
    copy(artifact, artifactTargetPath)
    entryPoint(
      "java",
      "-Dplay.http.secret.key=${PLAY_HTTP_SECRET_KEY}",
      "-jar",
      artifactTargetPath
    )
    expose(9000)
  }
}

//nativepackage + sbt-docker
//bashScriptExtraDefines += """addJava "-Dplay.http.secret.key=${PLAY_HTTP_SECRET_KEY}""""

//dockerfile in docker := {
//  val appDir: File = stage.value
//  val targetDir = "/app"
//
//  new Dockerfile {
//    from("java")
//    copy(appDir, targetDir)
//    entryPoint(s"$targetDir/bin/${executableScriptName.value}")
//    expose(9000)
//  }
//}
