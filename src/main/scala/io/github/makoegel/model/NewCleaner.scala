package io.github.makoegel.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class NewCleaner(name: String, team: Int)

object NewCleaner {
  implicit val newCleanerDecoder: Decoder[NewCleaner] = deriveDecoder[NewCleaner]
  implicit val newCleanerEncoder: Encoder[NewCleaner] = deriveEncoder[NewCleaner]
}
