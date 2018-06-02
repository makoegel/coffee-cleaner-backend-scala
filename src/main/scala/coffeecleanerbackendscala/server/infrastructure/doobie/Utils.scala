package coffeecleanerbackendscala.server.infrastructure.doobie

import cats.effect.Async
import coffeecleanerbackendscala.server.application.Config
import eu.timepit.refined.auto._
import doobie.hikari.HikariTransactor
import fs2.Stream

object Utils {

  def transactor[F[_]: Async](dbConfig: Config.Db): F[HikariTransactor[F]] =
    HikariTransactor.newHikariTransactor[F](
      driverClassName = dbConfig.driver,
      url = dbConfig.url,
      user = dbConfig.user,
      pass = dbConfig.password
    )

  def transactorStream[F[_]: Async](dbConfig: Config.Db): Stream[F, HikariTransactor[F]] =
    HikariTransactor.stream[F](
      driverClassName = dbConfig.driver,
      url = dbConfig.url,
      user = dbConfig.user,
      pass = dbConfig.password
    )
}
