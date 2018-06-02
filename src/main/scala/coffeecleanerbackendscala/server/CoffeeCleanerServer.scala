package coffeecleanerbackendscala.server

import cats.effect.IO
import eu.timepit.refined.auto._
import coffeecleanerbackendscala.server.application.{Config, Context}
import fs2.StreamApp.ExitCode
import fs2.StreamApp
import org.http4s.server.blaze.BlazeBuilder
import scala.concurrent.ExecutionContext.Implicits.global

object CoffeeCleanerServer extends StreamApp[IO] {

  override def stream(args: List[String], requestShutdown: IO[Unit]): fs2.Stream[IO, ExitCode] =
    Context.prepare[IO].flatMap { ctx =>
      runServer(ctx.config.http).serve
    }

  def runServer(httpConfig: Config.Http): BlazeBuilder[IO] =
    BlazeBuilder[IO]
      .bindHttp(httpConfig.port, httpConfig.host)
      .mountService(CoffeeCleanerService.service, "/")
}
