import javax.inject.{Inject, Singleton}

import org.slf4j.LoggerFactory
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

@Singleton
class ShutdownHook @Inject()(lifecycle: ApplicationLifecycle){

  private val logger = LoggerFactory.getLogger("shutdownhook")

  logger.info("Register shutdown hook")

  lifecycle.addStopHook{ () =>
    logger.info("Shutdown hook called")
    Future.successful(())
  }

}
