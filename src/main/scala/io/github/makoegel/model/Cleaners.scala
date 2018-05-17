package io.github.makoegel.model

case class Cleaners(cleaner: List[Cleaner])
case class Cleaner(id: Int, name: String, team: Int)

object Cleaner {

  val FranzCarlos = Cleaner(1, "Franz Carlos", 2)
  val Michl = Cleaner(2, "Michl", 2)
  val Michl2 = Cleaner(3, "Michl2", 3)
  val Juergen = Cleaner(5, "Jürgen", 1)
  val Jack = Cleaner(6, "Jack", 4)

  var allCleaners: List[Cleaner] = List(FranzCarlos, Michl, Michl2, Juergen, Jack)
  def addNewCleaner(cleaner: Cleaner): List[Cleaner] = allCleaners :+ cleaner
}
