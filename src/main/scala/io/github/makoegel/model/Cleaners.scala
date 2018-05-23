package io.github.makoegel.model
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.github.makoegel.model.Cleaner.allCleaners

case class Cleaners(cleaner: List[Cleaner])
case class Cleaner(id: String, name: String, team: String)
case class NewCleaner(name: String, team: String)

object Cleaners {
  implicit val cleanersDecoder: Decoder[Cleaners] = deriveDecoder[Cleaners]
  implicit val cleanersEncoder: Encoder[Cleaners] = deriveEncoder[Cleaners]
}

object Cleaner {
  implicit val cleanerDecoder: Decoder[Cleaner] = deriveDecoder[Cleaner]
  implicit val cleanerEncoder: Encoder[Cleaner] = deriveEncoder[Cleaner]

  val FranzCarlos = Cleaner("1", "Franz Carlos", "2")
  val Michl = Cleaner("2", "Michl", "2")
  val Michl2 = Cleaner("3", "Michl2", "3")
  val Juergen = Cleaner("5", "Jürgen", "1")
  val Jack = Cleaner("6", "Jack", "4")

  val allCleaners: List[Cleaner] = List(FranzCarlos, Michl, Michl2, Juergen, Jack)
}

object NewCleaner {
  implicit val newCleanerDecoder: Decoder[NewCleaner] = deriveDecoder[NewCleaner]
  implicit val newCleanerEncoder: Encoder[NewCleaner] = deriveEncoder[NewCleaner]

  def addNewCleaner(newCleaner: NewCleaner): Cleaner = {
    val maxId = allCleaners.sortWith(_.id > _.id).head.id
    val newCleanerId = maxId.toInt + 1

    Cleaner(newCleanerId.toString, newCleaner.name, newCleaner.team)
  }

}
