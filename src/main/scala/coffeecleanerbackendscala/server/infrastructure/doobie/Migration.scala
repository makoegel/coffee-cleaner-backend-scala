package coffeecleanerbackendscala.server.infrastructure.doobie

import java.sql.Connection

import cats.effect.{IO, Sync}
import coffeecleanerbackendscala.server.application.Config
import doobie._
import doobie.implicits._
import eu.timepit.refined.auto._
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.migration.jdbc.JdbcMigration

trait Migration extends JdbcMigration {
  def migrate: ConnectionIO[_]

  override def migrate(connection: Connection): Unit = {
    migrate.transact(Transactor.fromConnection[IO](connection)).unsafeRunSync()
    ()
  }
}

object Migration {

  def run[F[_]](dbConfig: Config.Db)(implicit F: Sync[F]): F[Int] =
    F.delay {
      val flyway = new Flyway
      val location = "filesystem:src/main/resources/db/migration"
      flyway.setDataSource(dbConfig.url, dbConfig.user, dbConfig.password)
      flyway.setLocations(location)
      flyway.migrate()
    }
}
