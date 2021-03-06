package coffeecleanerbackendscala.server

import cats.effect.IO
import org.http4s.circe._
import io.circe.syntax._
import coffeecleanerbackendscala.server.domain.model.{Cleaner, DelCleaner, NewCleaner}
import org.http4s._
import org.http4s.implicits._
import org.specs2.matcher.MatchResult

class CoffeeCleanerSpec extends org.specs2.mutable.Specification {

  "Cleaners" >> {
    "return 200" >> {
      uriReturns200()
    }
    "return cleaners" >> {
      uriReturnsCleaners()
    }

    "NewCleaner" >> {
      uriNewCleanerReturns200()

    }
    "return Success NewCleaner" >> {
      uriNewCleaner()
    }

    "UpdCleaner" >> {
      uriUpdCleanerReturns200()
    }
    "UpdCleanerNotFound" >> {
      uriUpdCleanerReturns404()
    }
    "return Success UpdCleaner" >> {
      uriUpdCleaner()
    }
    "return Success UpdCleanerNotFound" >> {
      uriUpdCleanerNotFound()
    }

    "DelCleaner" >> {
      uriDelCleanerReturns200()
    }
    "DelCleanerNotFound" >> {
      uriDelCleanerReturns404()
    }
    "return Success DelCleaner" >> {
      uriDelCleaner()
    }
    "return Success DelCleanerNotFound" >> {
      uriDelCleanerNotFound()
    }
  }

// return all Cleaners
  private[this] val retCleaners: Response[IO] = {
    val getCleaners = Request[IO](Method.GET, Uri.uri("/cc/api/cleaner"))
    CoffeeCleanerService.service.orNotFound(getCleaners).unsafeRunSync()
  }

  private[this] def uriReturns200(): MatchResult[Status] =
    retCleaners.status must beEqualTo(Status.Ok)

  private[this] def uriReturnsCleaners(): MatchResult[String] =
    retCleaners.as[String].unsafeRunSync() must beEqualTo(
      "{\"cleaner\":[{\"id\":1,\"name\":\"Franz Carlos\",\"team\":2}," +
        "{\"id\":2,\"name\":\"Michl\",\"team\":2},{\"id\":3,\"name\":\"Michl2\",\"team\":3}," +
        "{\"id\":5,\"name\":\"Jürgen\",\"team\":1},{\"id\":6,\"name\":\"Jack\",\"team\":4}]}"
    )

  // add new Cleaner
  private[this] val addNewCleaner: Response[IO] = {

    val newCleaner = NewCleaner("Max", 9)
    val body = newCleaner.asJson(NewCleaner.newCleanerEncoder)

    val addNewCleaner = Request[IO](Method.POST, Uri.uri("/cc/api/cleaner")).withBody(body).unsafeRunSync()
    CoffeeCleanerService.service.orNotFound(addNewCleaner).unsafeRunSync()
  }

  private[this] def uriNewCleanerReturns200(): MatchResult[Status] =
    addNewCleaner.status must beEqualTo(Status.Ok)

  private[this] def uriNewCleaner(): MatchResult[String] =
    addNewCleaner.as[String].unsafeRunSync() must beEqualTo(
      "{\n  \"id\" : 7,\n  \"name\" : \"Max\",\n  \"team\" : 9\n}")

  //update Cleaner
  // delete Cleaner
  private[this] val updCleaner: Response[IO] = {

    val updCleanerId = Cleaner(2, "Maxi", 6)
    val body = updCleanerId.asJson(Cleaner.cleanerEncoder)

    val updCleaner = Request[IO](Method.PUT, Uri.uri("/cc/api/cleaner")).withBody(body).unsafeRunSync()
    CoffeeCleanerService.service.orNotFound(updCleaner).unsafeRunSync()
  }

  private[this] def uriUpdCleanerReturns200(): MatchResult[Status] =
    updCleaner.status must beEqualTo(Status.Ok)

  private[this] def uriUpdCleaner(): MatchResult[String] =
    updCleaner.as[String].unsafeRunSync() must beEqualTo("Cleaner aktualisiert")

  // update Cleaner - id nicht gefunden
  private[this] val updCleanerNotFound: Response[IO] = {

    val updCleanerId = Cleaner(10, "Max", 6)
    val body = updCleanerId.asJson(Cleaner.cleanerEncoder)

    val updCleaner = Request[IO](Method.PUT, Uri.uri("/cc/api/cleaner")).withBody(body).unsafeRunSync()
    CoffeeCleanerService.service.orNotFound(updCleaner).unsafeRunSync()
  }

  private[this] def uriUpdCleanerReturns404(): MatchResult[Status] =
    updCleanerNotFound.status must beEqualTo(Status.NotFound)

  private[this] def uriUpdCleanerNotFound(): MatchResult[String] =
    updCleanerNotFound.as[String].unsafeRunSync() must
      beEqualTo("Cleaner wurde nicht gefunden und konnte nicht aktualisiert werden.")

// delete Cleaner
  private[this] val delCleaner: Response[IO] = {

    val delCleanerId = DelCleaner(2)
    val body = delCleanerId.asJson(DelCleaner.delCleanerEncoder)

    val delCleaner = Request[IO](Method.DELETE, Uri.uri("/cc/api/cleaner")).withBody(body).unsafeRunSync()
    CoffeeCleanerService.service.orNotFound(delCleaner).unsafeRunSync()
  }

  private[this] def uriDelCleanerReturns200(): MatchResult[Status] =
    delCleaner.status must beEqualTo(Status.Ok)

  private[this] def uriDelCleaner(): MatchResult[String] =
    delCleaner.as[String].unsafeRunSync() must beEqualTo("Cleaner gelöscht")

  // delete Cleaner - id nicht gefunden
  private[this] val delCleanerNotFound: Response[IO] = {

    val delCleanerId = DelCleaner(10)
    val body = delCleanerId.asJson(DelCleaner.delCleanerEncoder)

    val delCleaner = Request[IO](Method.DELETE, Uri.uri("/cc/api/cleaner")).withBody(body).unsafeRunSync()
    CoffeeCleanerService.service.orNotFound(delCleaner).unsafeRunSync()
  }

  private[this] def uriDelCleanerReturns404(): MatchResult[Status] =
    delCleanerNotFound.status must beEqualTo(Status.NotFound)

  private[this] def uriDelCleanerNotFound(): MatchResult[String] =
    delCleanerNotFound.as[String].unsafeRunSync() must
      beEqualTo("Cleaner wurde nicht gefunden und konnte nicht gelöscht werden.")
}
