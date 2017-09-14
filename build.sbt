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
  new Dockerfile {
    from("anapsix/alpine-java:8u141b15_jdk")
    copy(assembly.value, "/app/assembly.jar")
    copy(file("./start.sh"), "/app/start.sh")
    run("chmod", "+x", "/app/start.sh")
    expose(9000)
    cmd("/app/start.sh")
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
