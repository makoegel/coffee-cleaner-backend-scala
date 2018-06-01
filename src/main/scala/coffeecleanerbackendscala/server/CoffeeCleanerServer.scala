package coffeecleanerbackendscala.server

import cats.effect.{Effect, IO}
import fs2.StreamApp
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext

object CoffeeCleanerServer extends StreamApp[IO] {
  import scala.concurrent.ExecutionContext.Implicits.global

  def stream(args: List[String], requestShutdown: IO[Unit]) = ServerStream.stream[IO]
}

object ServerStream {

  def coffeeCleanerService[F[_]: Effect] = new CoffeeCleanerService[IO].service

  def stream[F[_]: Effect](implicit ec: ExecutionContext) =
    BlazeBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .mountService(coffeeCleanerService, "/")
      .serve
}
