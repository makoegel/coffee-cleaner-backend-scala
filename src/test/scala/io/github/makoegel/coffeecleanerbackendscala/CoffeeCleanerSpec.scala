package io.github.makoegel.coffeecleanerbackendscala

import cats.effect.IO
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

    /*    "NewCleaner" >> {
      uriNewCleanerReturns200()

    }
    "return Success NewCleaner" >> {
      uriNewCleaner()
    }*/
  }

  private[this] val retCleaners: Response[IO] = {
    val getCleaners = Request[IO](Method.GET, Uri.uri("/cc/api/cleaner"))
    new CoffeeCleanerService[IO].service.orNotFound(getCleaners).unsafeRunSync()
  }

  private[this] def uriReturns200(): MatchResult[Status] =
    retCleaners.status must beEqualTo(Status.Ok)

  private[this] def uriReturnsCleaners(): MatchResult[String] =
    retCleaners.as[String].unsafeRunSync() must beEqualTo(
      "{\"cleaner\":[{\"id\":1,\"name\":\"Franz Carlos\",\"team\":2}," +
        "{\"id\":2,\"name\":\"Michl\",\"team\":2},{\"id\":3,\"name\":\"Michl2\",\"team\":3}," +
        "{\"id\":5,\"name\":\"Jürgen\",\"team\":1},{\"id\":6,\"name\":\"Jack\",\"team\":4}]}"
    )

  /*  private[this] val addCleaner: Response[IO] = {

    val cleaner = new Cleaner(7, "Marion", 8)
    val cleanerAsJson = cleaner.asJson
    val addCleaner = Request[IO](Method.POST, Uri.uri("/cc/api/newcleaner"))
    addCleaner.withBody(cleanerAsJson)
    new CoffeeCleanerService[IO].service.orNotFound(addCleaner).unsafeRunSync()
  }*/

  /* private[this] def uriNewCleanerReturns200(): MatchResult[Status] =
    addCleaner.status must beEqualTo(Status.Ok)

  private[this] def uriNewCleaner(): MatchResult[String] = {
    println(addCleaner.as[String].unsafeRunSync())
    addCleaner.as[String].unsafeRunSync() must beEqualTo("User gespeichert")
  }*/
}
