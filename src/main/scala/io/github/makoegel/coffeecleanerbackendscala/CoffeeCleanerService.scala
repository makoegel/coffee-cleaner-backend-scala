package io.github.makoegel.coffeecleanerbackendscala

import cats.effect._
import io.github.makoegel.model.{Cleaner, Cleaners}
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import io.circe.generic.auto._
import io.circe.syntax._

class CoffeeCleanerService[F[_]: Effect] extends Http4sDsl[IO] {

  implicit val decoder = jsonOf[IO, Cleaner]

  val service: HttpService[IO] = {
    HttpService[IO] {
      case GET -> Root / cc / api / cleaner =>
        val res = IO(Cleaners(Cleaner.allCleaners).asJson)
        Ok(res)
      case req @ POST -> Root / cc / api / newcleaner =>
        for {
          cleaner <- req.as[Cleaner]
          resp <- Ok("User hinzugefügt")
        } yield (resp)
      case _ => MethodNotAllowed("Method not supported")
    }
  }
}
