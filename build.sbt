name := """play-docker-showcase"""
organization := "com.github.dpfeiffer"

version := "1.0"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, sbtdocker.DockerPlugin, JavaAppPackaging)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.1" % Test

bashScriptExtraDefines += """addJava "-Dplay.http.secret.key=${PLAY_HTTP_SECRET_KEY}""""

dockerfile in docker := {
  val appDir: File = stage.value
  val targetDir = "/app"

  new Dockerfile {
    from("java")
    copy(appDir, targetDir)
    entryPoint(s"$targetDir/bin/${executableScriptName.value}")
    expose(9000)
  }
}