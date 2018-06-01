package coffeecleanerbackendscala.server.domain.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Cleaners(cleaner: List[Cleaner])

object Cleaners {
  implicit val cleanersDecoder: Decoder[Cleaners] = deriveDecoder[Cleaners]
  implicit val cleanersEncoder: Encoder[Cleaners] = deriveEncoder[Cleaners]
}
