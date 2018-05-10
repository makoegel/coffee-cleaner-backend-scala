package io.github.makoegel.coffeecleanerbackendscala

import cats.effect.Effect
import io.github.makoegel.model.{Cleaner, Cleaners}
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import io.circe.generic.auto._
import io.circe.syntax._

class CoffeeCleanerService[F[_]: Effect] extends Http4sDsl[F] {

  val service: HttpService[F] = {
    HttpService[F] {
      case GET -> Root / cc / api / cleaner =>
        Ok(Cleaners(Cleaner.findCleaners).asJson)
      case _ => MethodNotAllowed("Method not supported")
    }
  }
}
