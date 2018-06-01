package coffeecleanerbackendscala.server.domain.service

import coffeecleanerbackendscala.server.domain.model.{Cleaner, NewCleaner}

object CleanerService {

  val FranzCarlos = Cleaner(1, "Franz Carlos", 2)
  val Michl = Cleaner(2, "Michl", 2)
  val Michl2 = Cleaner(3, "Michl2", 3)
  val Juergen = Cleaner(5, "Jürgen", 1)
  val Jack = Cleaner(6, "Jack", 4)

  var allCleaners: List[Cleaner] = List(FranzCarlos, Michl, Michl2, Juergen, Jack)

  def findCleaner(delCleanerId: Int): Option[Cleaner] =
    allCleaners.find(c => c.id == delCleanerId)

  def deleteCleaner(delCleanerId: Int): Unit =
    allCleaners = for {
      cleaner <- allCleaners
      if delCleanerId != cleaner.id
    } yield cleaner

  def addNewCleaner(newCleaner: NewCleaner): Cleaner = {
    val maxId = allCleaners.sortWith(_.id > _.id).head.id
    val newCleanerId = maxId.toInt + 1
    val cleaner: Cleaner = Cleaner(newCleanerId, newCleaner.name, newCleaner.team)
    allCleaners = allCleaners :+ cleaner
    cleaner
  }

  def updateCleaner(updCleaner: Cleaner): Unit =
    allCleaners = allCleaners.map { cleaner =>
      if (updCleaner.id == cleaner.id) updCleaner else cleaner
    }

}
