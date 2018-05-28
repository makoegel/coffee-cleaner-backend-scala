package io.github.makoegel.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class DelCleaner(id: Int)

object DelCleaner {
  implicit val delCleanerDecoder: Decoder[DelCleaner] = deriveDecoder[DelCleaner]
  implicit val delCleanerEncoder: Encoder[DelCleaner] = deriveEncoder[DelCleaner]
}
