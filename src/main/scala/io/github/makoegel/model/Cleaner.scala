package io.github.makoegel.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Cleaner(id: String, name: String, team: String)

object Cleaner {
  implicit val cleanerDecoder: Decoder[Cleaner] = deriveDecoder[Cleaner]
  implicit val cleanerEncoder: Encoder[Cleaner] = deriveEncoder[Cleaner]
}
