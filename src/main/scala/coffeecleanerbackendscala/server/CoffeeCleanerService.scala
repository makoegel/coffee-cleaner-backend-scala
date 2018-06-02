package coffeecleanerbackendscala.server

import cats.effect._
import coffeecleanerbackendscala.server.domain.model.{Cleaner, Cleaners, DelCleaner, NewCleaner}
import coffeecleanerbackendscala.server.domain.service.CleanerService
import org.http4s._
import org.http4s.circe.{jsonOf, _}
import org.http4s.dsl.Http4sDsl
import io.circe.syntax._

object CoffeeCleanerService {

  val dsl = Http4sDsl[IO]
  import dsl._

  val service: HttpService[IO] = {
    HttpService[IO] {
      case GET -> Root / cc / api / cleaner =>
        val res = IO(Cleaners(CleanerService.allCleaners).asJson)
        Ok(res)
      case req @ POST -> Root / cc / api / cleaner =>
        req.as(implicitly, jsonOf[IO, NewCleaner]).flatMap { newCleaner =>
          {
            val cleaner: Cleaner = CleanerService.addNewCleaner(newCleaner)
            Ok(cleaner.asJson.toString())
          }
        }
      case req @ PUT -> Root / cc / api / cleaner =>
        req.as(implicitly, jsonOf[IO, Cleaner]).flatMap { updCleaner =>
          {
            val cleaner: Option[Cleaner] = CleanerService.findCleaner(updCleaner.id)
            cleaner match {
              case Some(cleaner) => {
                CleanerService.updateCleaner(updCleaner)
                Ok("Cleaner aktualisiert")
              }
              case None => NotFound("Cleaner wurde nicht gefunden und konnte nicht aktualisiert werden.")
            }
          }
        }
      case req @ DELETE -> Root / cc / api / cleaner =>
        req
          .as(implicitly, jsonOf[IO, DelCleaner])
          .flatMap { delCleaner =>
            {
              val cleaner: Option[Cleaner] = CleanerService.findCleaner(delCleaner.id)
              cleaner match {
                case Some(cleaner) => {
                  CleanerService.deleteCleaner(cleaner.id)
                  Ok("Cleaner gelöscht")
                }
                case None => NotFound("Cleaner wurde nicht gefunden und konnte nicht gelöscht werden.")
              }
            }
          }
      case _ => MethodNotAllowed("Method not supported")
    }
  }
}
