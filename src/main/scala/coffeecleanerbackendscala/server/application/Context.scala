package coffeecleanerbackendscala.server.application

import cats.effect.Async
import coffeecleanerbackendscala.server.infrastructure.doobie.{Migration, Utils}
import doobie.Transactor
import fs2.Stream

final case class Context[F[_]](
    config: Config,
    transactor: Transactor[F]
)

object Context {

  def prepare[F[_]: Async]: Stream[F, Context[F]] =
    for {
      config <- Stream.eval(Config.load[F])
      _ <- Stream.eval(Migration.run[F](config.db))
      transactor <- Utils.transactorStream[F](config.db)
    } yield Context(config, transactor)
}
