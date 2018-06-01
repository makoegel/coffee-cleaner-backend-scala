package coffeecleanerbackendscala.server.domain.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Cleaner(id: Int, name: String, team: Int)

object Cleaner {
  implicit val cleanerDecoder: Decoder[Cleaner] = deriveDecoder[Cleaner]
  implicit val cleanerEncoder: Encoder[Cleaner] = deriveEncoder[Cleaner]
}
