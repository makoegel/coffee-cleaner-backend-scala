package io.github.makoegel.coffeecleanerbackendscala

import cats.effect._
import io.github.makoegel.model.{Cleaner, Cleaners, NewCleaner}
import org.http4s._
import org.http4s.circe.{jsonOf, _}
import org.http4s.dsl.Http4sDsl
import io.circe.syntax._

class CoffeeCleanerService[F[_]: Effect] extends Http4sDsl[IO] {

  val service: HttpService[IO] = {
    HttpService[IO] {
      case GET -> Root / cc / api / cleaner =>
        val res = IO(Cleaners(Cleaner.allCleaners).asJson)
        Ok(res)
      case req @ POST -> Root / cc / api / newcleaner =>
        req.as(implicitly, jsonOf[IO, NewCleaner]).flatMap { newCleaner =>
          {
            val cleaner: Cleaner = NewCleaner.addNewCleaner(newCleaner)
            Ok(cleaner.asJson.toString())
          }
        }
      case _ => MethodNotAllowed("Method not supported")
    }
  }
}
