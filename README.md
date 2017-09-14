# Play on Docker

These code samples are based on the playframework/play-scala-seed.g8 Giter8 template and extended with the sbt-docker and sbt-assembly
plugin to build a Docker image containing a very simplistic Play application.

Prerequesites:
* Docker 
* SBT 0.13.15+

You can build the docker image with `sbt docker` and run it `docker run -it --rm --name showcase -p 9000:9000 com.github.dpfeiffer/play-docker-showcase`.

The Play application is now accessible in your Docker environment (localhost or Docker Machine IP) on port 9000.
